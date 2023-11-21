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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Streamable;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AngestelltenControllerUnitTest {

	@Mock
	private AngestelltenVerwaltung angestelltenVerwaltung;
	private Model                  model;

	private AngestelltenController controller;

	@BeforeEach
	void setUp () {
		controller = new AngestelltenController(angestelltenVerwaltung);
		model = new ExtendedModelMap();
	}

	@Test
	void anstellenOkay () {
		var form = new NeuerAngestelltenForm("Test", "Person", "test@te.de", "12345", "ANGESTELLTER");
		controller.anstellen(form);

		// Prüfen, ob die Verwaltungsmethode aufgerufen wurde
		verify(angestelltenVerwaltung).anstellen(any());
	}


	@Test
	void angestellte () {
		var angestellte = Streamable.of(new Bibliothekar(), new Reinigungskraft(), new Restaurateur());
		when(angestelltenVerwaltung.findAll()).thenReturn(angestellte);

		controller.angestellte(model);

		// Prüfen, ob die Verwaltungsmethode aufgerufen wurde
		verify(angestelltenVerwaltung).findAll();
		assertTrue(model.asMap().containsValue(angestellte));
	}


	@Test
	void enlassenOkay () {
	}

	@Test
	void deaktivieren () {
	}

	@Test
	void aktivieren () {
	}

	@Test
	void zeigeAngestelltenOkay () {
		// Der test ist erfolgreich aber der entsprechende Integrationstest schlägt fehl
		var angestellter = new Bibliothekar();

		controller.zeigeAngestellten(model, angestellter);

		var modelAngestellter = model.getAttribute("angestellter");
		assertEquals(angestellter, modelAngestellter);
	}
}