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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@Tag("unit")
@Tag("component")
@ExtendWith(MockitoExtension.class)
class AngestelltenConverterUnitTest {

	@Mock
	private AngestellterRepository angestellterRepository;

	@InjectMocks
	private AngestelltenConverter converter;

	@BeforeEach
	void setUp () {
	}

	@Test
	void convertFindetErgebnis () {
		var ID = UUID.randomUUID();
		var angestellter = mock(Angestellter.class);
		when(angestellterRepository.findById(ID)).thenReturn(Optional.of(angestellter));

		var ergebnis = converter.convert(ID.toString());

		assertEquals(angestellter, ergebnis,
				"Die ID des gefundenen Angestellten stimmt nicht mit der gesuchten überein");
	}

	@Test
	void convertFindetKeinErgebnis () {

		assertThrowsExactly(NoSuchElementException.class, () ->
				converter.convert(UUID.randomUUID().toString()));
	}
}