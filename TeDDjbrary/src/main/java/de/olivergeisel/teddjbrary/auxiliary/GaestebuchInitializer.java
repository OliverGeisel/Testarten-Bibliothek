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


package de.olivergeisel.teddjbrary.auxiliary;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.salespointframework.core.DataInitializer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Component
public class GaestebuchInitializer implements DataInitializer {


	private final ResourceLoader resourceLoader;
	private final PostRepository postRepository;

	public GaestebuchInitializer(ResourceLoader resourceLoader, PostRepository postRepository) {
		this.resourceLoader = resourceLoader;
		this.postRepository = postRepository;
	}

	/**
	 * Called on application startup to trigger data initialization. Will run inside a transaction.
	 */
	@Override
	public void initialize() {
		var post1 = new Post("Liebe dich 3000!", "Eisenmann", "Held");
		var post2 = new Post("Hoffnung ist mein Antrieb. Je schlechter meine Chancen stehen umso mehr bin ich bereit "
							 + "alles zu geben. Denn dann weiß ich, es war es wert gegen die Welt zu spielen.",
				"Unbekannt",
				"Ein wahrer Held");
		var list = new ArrayList<Post>();
		list.add(post1);
		list.add(post2);
		try {
			Resource resource = resourceLoader.getResource("classpath:" + "zitate.csv");
			CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()));
			var zitate = reader.readAll().stream().filter(it -> it.length == 3)
							   .collect(java.util.stream.Collectors.toList());
			var head = zitate.remove(0);
			for (int i = 0; i < 5; i++) {
				var index = (int) (Math.random() * zitate.size());
				var zitat = zitate.remove(index);
				list.add(new Post(zitat[0], zitat[1], zitat[2]));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (CsvException e) {
			throw new RuntimeException(e);
		}
		postRepository.saveAll(list);
	}
}
