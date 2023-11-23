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

import de.olivergeisel.teddjbrary.core.Buch;
import de.olivergeisel.teddjbrary.core.BuchRepository;
import de.olivergeisel.teddjbrary.core.ISBN;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
@Tag("repository")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureMockMvc
class RegalRepositoryTest {

	private final ISBN ISBN_1 = ISBN.fromString("978-1-2345-678-9");
	private final ISBN ISBN_2 = ISBN.fromString("978-1-2345-678-0");

	private final RegalCode       CODE_1        = new RegalCode("P", 1, "S");
	private final RegalCode       CODE_2        = new RegalCode("P", 2, "S");
	private final Regal           DEFAULT_REGAL = new Regal(3, 4, CODE_1);
	@Autowired
	private       BuchRepository  buecher;
	@Autowired
	private       RegalRepository regale;


	@BeforeEach
	void setUp () {

	}

	@AfterEach
	void tearDown () {
	}

	@Test
	@DisplayName("Speichern eines Regals erhöht die Anzahl der Regale um 1.")
	void count () {
		var regal = new Regal(3, 4, CODE_1);
		regale.save(regal);
		assertEquals(1, regale.count(), "Anzahl der Regale ist nicht 1.");
	}

	@Test
	@DisplayName("FindAll gibt alle Regale zurück.")
	void findAll () {
		assertEquals(0, regale.findAll().toList().size(), "Anzahl muss zu beginn 0 sein.");

		regale.save(DEFAULT_REGAL);
		var ergebnis = regale.findAll().toList();

		assertEquals(1, ergebnis.size(), "Anzahl muss nach dem Speichern 1 sein.");
		assertEquals(CODE_1, ergebnis.get(0).getCode(), "Code muss nach dem Speichern gleich sein.");
	}

	@Test
	@DisplayName("Finde Regal zu passender ISBN. Nur ein Buch im Regal.")
	void findByInhalt_Buecher_Isbn_1B_okay () {
		var buch = new Buch("Testbuch", ISBN_1);
		buch = buecher.save(buch);
		DEFAULT_REGAL.hineinStellen(buch);
		regale.save(DEFAULT_REGAL);

		var gefundenesRegal = regale.findByInhalt_Buecher_Isbn(ISBN_1);
		assertTrue(gefundenesRegal.isPresent(), "Regal mit ISBN 1 nicht gefunden.");
		assertEquals(CODE_1, gefundenesRegal.get().getCode(), "Regal mit ISBN 1 hat falschen Code.");
	}

	@Test
	@DisplayName("Finde Regal zu passender ISBN. Mehrere Bücher im Regal.")
	void findByInhalt_Buecher_Isbn_2B_okay () {
		var buch = new Buch("Testbuch", ISBN_1);
		buch = buecher.save(buch);
		DEFAULT_REGAL.hineinStellen(buch);
		var buch2 = new Buch("Versuchsbuch", ISBN_2);
		buch2 = buecher.save(buch2);
		DEFAULT_REGAL.hineinStellen(buch2);
		regale.save(DEFAULT_REGAL);

		var gefundenesRegal = regale.findByInhalt_Buecher_Isbn(ISBN_2);

		assertTrue(gefundenesRegal.isPresent(), "Regal mit ISBN 2 nicht gefunden.");
		assertEquals(CODE_1, gefundenesRegal.get().getCode(), "Regal mit ISBN 2 hat falschen Code.");
	}

	@Test
	@DisplayName("Finde Regal zu passender ISBN. Mehrere Regale.")
	void findByInhalt_Buecher_Isbn_2R_okay () {
		var buch = new Buch("Testbuch", ISBN_1);
		buch = buecher.save(buch);
		DEFAULT_REGAL.hineinStellen(buch);
		regale.save(DEFAULT_REGAL);
		var buch2 = new Buch("Versuchsbuch", ISBN_2);
		buch2 = buecher.save(buch2);
		var regal2 = new Regal(3, 4, CODE_2);
		regal2.hineinStellen(buch2);
		regale.save(regal2);

		var gefundenesRegal = regale.findByInhalt_Buecher_Isbn(ISBN_1);

		assertTrue(gefundenesRegal.isPresent(), "Regal mit ISBN 1 nicht gefunden.");
		assertEquals(CODE_1, gefundenesRegal.get().getCode(), "Regal mit ISBN 1 hat falschen Code.");
		gefundenesRegal = regale.findByInhalt_Buecher_Isbn(ISBN_2);
		assertTrue(gefundenesRegal.isPresent(), "Regal mit ISBN 2 nicht gefunden.");
		assertEquals(CODE_2, gefundenesRegal.get().getCode(), "Regal mit ISBN 2 hat falschen Code.");
	}

	@Test
	@DisplayName("Finde volle Regale bei 0 Regalen.")
	void getNichtVolleRegale_Keine_Regale () {
		assertEquals(0, regale.count(), "Anzahl der Regale muss 0 sein.");

		var ergebnis = regale.getNichtVolleRegale();

		assertEquals(0, ergebnis.toList().size(), "Es darf kein Regal gefunden werden.");
	}

	@Test
	@DisplayName("Finde volle Regale bei 1 Regal.")
	void getNichtVolleRegale_1_leeres_Regale () {
		regale.save(DEFAULT_REGAL);
		assertEquals(1, regale.count(), "Anzahl der Regale muss 0 sein.");

		var ergebnis = regale.getNichtVolleRegale();

		assertEquals(0, ergebnis.toList().size(), "Es darf kein Regal gefunden werden.");
	}

	@Test
	@DisplayName("Finde 1 volles Regale bei 2 Regalen.")
	void getNichtVolleRegale_1_leeres_1_volles_Regale () {
		for (int i = 0; i < 12; i++) {
			var buch = new Buch("Testbuch", ISBN_1);
			buch = buecher.save(buch);
			DEFAULT_REGAL.hineinStellen(buch);
		}
		regale.save(DEFAULT_REGAL);
		regale.save(new Regal(3, 4, CODE_2));
		assertEquals(2, regale.count(), "Anzahl der Regale muss 2 sein.");

		var ergebnis = regale.getNichtVolleRegale();

		assertEquals(1, ergebnis.toList().size(), "Es muss 1 Regal gefunden werden.");
	}

	@Test
	void findByBuchId_Vorhanden () {
		for (int i = 0; i < 12; i++) {
			var buch = new Buch("Testbuch", ISBN_1);
			buch = buecher.save(buch);
			DEFAULT_REGAL.hineinStellen(buch);
		}
		regale.save(DEFAULT_REGAL);
	}

	@Test
	@DisplayName("Finde kein Regal zu Buch-ID. Keine Bücher.")
	void findByBuchId_Nicht_Vorhanden () {
		regale.save(DEFAULT_REGAL);

		var ergebnis = regale.findByBuchId(UUID.randomUUID());
		assertNull(ergebnis, "Es darf kein Regal gefunden werden.");
	}

	@Test
	@DisplayName("Finde kein Regal zu Buch-ID. Bücher vorhanden.")
	void findByBuchId_Nicht_Vorhanden_aber_Buecher () {
		for (int i = 0; i < 12; i++) {
			var buch = new Buch("Testbuch", ISBN_1);
			buch = buecher.save(buch);
			DEFAULT_REGAL.hineinStellen(buch);
		}
		regale.save(DEFAULT_REGAL);

		var ergebnis = regale.findByBuchId(UUID.randomUUID());

		assertNull(ergebnis, "Es darf kein Regal gefunden werden.");
	}

	@Test
	@DisplayName("Finde kein Regal zu Buch-ID. Bücher vorhanden.")
	void findByBuchId_Vorhanden_1_Buch () {
		var buch = new Buch("Testbuch", ISBN_1);
		buch = buecher.save(buch);
		DEFAULT_REGAL.hineinStellen(buch);
		var regal = regale.save(DEFAULT_REGAL);

		var ergebnis = regale.findByBuchId(buch.getId());

		assertNotNull(ergebnis, "Es muss ein Regal gefunden werden.");
		assertEquals(regal, ergebnis, "Es muss das richtige Regal gefunden werden.");
	}
}
