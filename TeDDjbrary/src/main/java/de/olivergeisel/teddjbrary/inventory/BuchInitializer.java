/*
 * Copyright 2023 Oliver Geisel
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package de.olivergeisel.teddjbrary.inventory;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import de.olivergeisel.teddjbrary.core.Buch;
import de.olivergeisel.teddjbrary.core.BuchRepository;
import de.olivergeisel.teddjbrary.core.ISBN;
import org.salespointframework.core.DataInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Order(1)
public class BuchInitializer implements DataInitializer {

	private final BuchRepository buchRepo;
	private final ResourceLoader resourceLoader;
	private final Logger         logger = LoggerFactory.getLogger(BuchInitializer.class);
	@Value("${app.buecher.count:1000}")
	private int buecherCount;

	public BuchInitializer(BuchRepository buchRepo, ResourceLoader resourceLoader) {
		this.buchRepo = buchRepo;
		this.resourceLoader = resourceLoader;
	}

	/**
	 * Called on application startup to trigger data initialization. Will run inside a transaction.
	 */
	@Override
	public void initialize() {
		AtomicInteger count = new AtomicInteger();
		var resource = resourceLoader.getResource("classpath:buecher.csv");
		CSVReaderBuilder builder = null;
		try {
			builder = new CSVReaderBuilder(new InputStreamReader(resource.getInputStream()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		logger.info("Initialisiere {} Bücher...", buecherCount);
		var parser = new CSVParserBuilder();
		parser.withSeparator(';').withQuoteChar('"').withEscapeChar('\\').withIgnoreLeadingWhiteSpace(true);
		builder.withSkipLines(1).withCSVParser(parser.build()).withVerifyReader(false);
		try (CSVReader reader = builder.build()) {
			reader.skip(1);
			while (count.get() < buecherCount) {
				if (reader.peek() == null) {
					break;
				}
				String[] line = reader.readNext();
				try {
					// All ISBNs are ISBN-10 input --> convert to ISBN-13
					ISBN isbn = ISBN.fromStringOhneTrennung(line[0]);
					var titel = line[1];
					var autor = line[2];
					var uri = URI.create(line[6]);
					var buch = new Buch(titel, autor, isbn, uri);
					buchRepo.save(buch);
				} catch (IllegalArgumentException e) {
					logger.warn("Fehler beim Initialisieren der Bücher in Zeile {}", count, e);
				} finally {
					count.getAndIncrement();
				}
			}
		} catch (Exception e) {
			logger.error("Unerwarteter Fehler beim Initialisieren der Bücher", e);
		}
		logger.info("Bücher initialisiert: {} wurden gefunden.", buchRepo.count());
	}
}

@Component
@Order(2)
class RegalInitializer implements DataInitializer {

	private final RegalRepository regalRepo;
	private final BuchRepository  buchRepo;
	private final Logger          logger = LoggerFactory.getLogger(RegalInitializer.class);


	public RegalInitializer(RegalRepository regalRepo, BuchRepository buchRepo) {
		this.regalRepo = regalRepo;
		this.buchRepo = buchRepo;
	}

	@Override
	public void initialize() {

		int regalCount = 1;
		Regal regal = new Regal();
		for (var buch : buchRepo.findAll()) {
			if (regal.isVoll()) {
				regalRepo.save(regal);
				regal = new Regal();
				regalCount++;
			}
			regal.hineinStellen(buch);
		}
		regalRepo.save(regal);
		logger.info("Regale initialisiert: {} wurden erstellt.", regalCount);
	}
}
