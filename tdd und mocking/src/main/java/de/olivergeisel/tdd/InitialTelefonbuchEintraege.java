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

package de.olivergeisel.tdd;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;

@Component
@Order(1)
public class InitialTelefonbuchEintraege implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(InitialTelefonbuchEintraege.class);
	private TelefonbuchService telefonbuch;


	public InitialTelefonbuchEintraege (TelefonbuchService telefonbuch) {
		this.telefonbuch = telefonbuch;
	}

	/**
	 * Callback used to run the bean.
	 *
	 * @param args incoming main method arguments
	 * @throws Exception on error
	 */
	@Override
	public void run (String... args) throws Exception {
		try (CSVParser csvParser = CSVParser.parse(new InputStreamReader(
						getClass().getClassLoader().getResourceAsStream("Telefonbuch.csv")),
				CSVFormat.Builder.create().setDelimiter(",").setSkipHeaderRecord(true).
								 setHeader("Vorname", "Nachname", "Telefonnummer", "Land", "Ort", "PLZ",
										 "Straße", "Nummer")
								 .build())) {
			csvParser.forEach(line -> {
				var eintrag = new TelefonbuchEintrag();
/* Todo Auskommentieren wenn TelefonbuchEintrag fertig ist

				eintrag.setVorname(line.get("Vorname").strip());
				eintrag.setNachname(line.get("Nachname").strip());
				eintrag.setTelefonnummer(line.get("Telefonnummer").strip());
				eintrag.setStrasse(line.get("Straße").strip());
				eintrag.setHausnummer(line.get("Nummer").strip());
				eintrag.setPostleitzahl(line.get("PLZ").strip());
				eintrag.setOrt(line.get("Ort").strip());
				eintrag.setLand(line.get("Land").strip());
*/
				telefonbuch.speichern(eintrag);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(""" 
				InitialTelefonbuchEintraege: Initialisierung abgeschlossen
				Es wurden {} Einträge in das Telefonbuch eingetragen.""", telefonbuch.anzahl());

	}


}
