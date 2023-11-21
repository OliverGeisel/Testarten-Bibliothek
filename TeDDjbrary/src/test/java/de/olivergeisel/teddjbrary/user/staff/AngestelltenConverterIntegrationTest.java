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

package de.olivergeisel.teddjbrary.user.staff;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
@Tag("component")
@SpringBootTest
class AngestelltenConverterIntegrationTest {

	@Autowired
	private AngestellterRepository repository;

	@Autowired
	private AngestelltenConverter converter;

	@Test
	void convertFindetKeinErgebnis () {
		assertThrowsExactly(NoSuchElementException.class, () ->
				converter.convert("12345678-1234-1234-1234-123456789012"));
	}

	@Test
	void convertFindetErgebnis () {
		var alle = repository.findAll().iterator();
		assertTrue(alle.hasNext(), "Keine Angestellten in der Datenbank");
		var erster = alle.next();
		var ergebnis = converter.convert(erster.getId().toString());
		assertEquals(erster, ergebnis, "Die ID des gefundenen Angestellten stimmt nicht mit der gesuchten überein");
	}
}