package de.olivergeisel.teddjbrary.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("unit")
@Tag("repository")
@DataJpaTest()
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureMockMvc
class BuchRepositoryTest {

	private final ISBN ISBN_1 = ISBN.fromString("978-1-2345-678-9");
	private final ISBN ISBN_2 = ISBN.fromString("978-1-2345-678-0");
	private       Buch DEFAULT_BUCH;

	@Autowired
	private BuchRepository buchRepository;

	@BeforeEach
	void setUp () {
		DEFAULT_BUCH = new Buch("Testbuch", ISBN_1);
	}

	@Test
	@DisplayName("Anzahl der zu Beginn ist 0.")
	void count_leer () {
		assertEquals(0, buchRepository.count(), "Anzahl der Bücher muss 0 sein.");
	}

	@Test
	@DisplayName("Speichern eines Buches erhöht die Anzahl der Bücher um 1.")
	void count_1_buch () {
		buchRepository.save(DEFAULT_BUCH);
		assertEquals(1, buchRepository.count(), "Anzahl der Bücher ist nicht 1.");
	}

	@Test // Beispiel
	@DisplayName("Speichern eines Buches erhöht die Anzahl der Bücher um 1. Auch gleiche Bücher werden gezählt.")
	void count_alle () {
		buchRepository.save(DEFAULT_BUCH);
		for (int i = 0; i < 10; i++) {
			buchRepository.save(new Buch("Testbuch", ISBN_1));
		}
		assertEquals(11, buchRepository.count(), "Jedes Buch muss gezählt werden.");
	}

	@Test // Beispiel
	@DisplayName("Speichern eines Buches erhöht die Anzahl der Bücher um 1. Auch gleiche Bücher werden gezählt.")
	void count_alle_10 () {
		for (int i = 0; i < 10; i++) {
			buchRepository.save(new Buch("Testbuch", ISBN_1));
			assertEquals(i + 1, buchRepository.count(), "Jedes Buch muss gezählt werden. i war " + i);
		}
	}


	@Test
	@DisplayName("Finde alle Bücher. Kein Buch vorhanden.")
	void findAll_leer () {
		var ergebnis = buchRepository.findAll();

		assertEquals(0, ergebnis.stream().count(), "Es dürfen keine Bücher gefunden werden, wenn keine "
												   + "Bücher vorhanden sind.");
	}

	@Test
	@DisplayName("Finde alle Bücher. 1 Buch vorhanden.")
	void findAll_1_Buch () {
		buchRepository.save(DEFAULT_BUCH);

		var ergebnis = buchRepository.findAll();

		assertEquals(1, ergebnis.stream().count(), "Es muss 1 Buch gefunden werden.");
	}

	@Test
	@DisplayName("Finde alle Bücher. 10 Bücher vorhanden.")
	void findAll_10_Buecher () {
		for (int i = 0; i < 10; i++) {
			buchRepository.save(new Buch("Testbuch " + i, ISBN.fromString("978-1-2345-678-" + i)));
		}

		var ergebnis = buchRepository.findAll();

		assertEquals(10, ergebnis.stream().count(), "Es müssen 10 Bücher gefunden werden.");
	}

	@Test
	@DisplayName("Finde alle Bücher in Page. Keine Bücher vorhanden.")
	void findAllPage_leer () {
		var page = PageRequest.of(0, 10);
		var ergebnis = buchRepository.findAll(page);

		assertEquals(0, ergebnis.getTotalPages(), "Es dürfen keine Pages vorhanden sein, wenn keine "
												  + "Bücher vorhanden sind.");
	}

	@Test
	@DisplayName("Finde alle Bücher in Page. 1 Buch vorhanden.")
	void findAllPage_1_Buch () {
		buchRepository.save(DEFAULT_BUCH);
		var page = PageRequest.of(0, 10);
		var ergebnis = buchRepository.findAll(page);

		assertEquals(1, ergebnis.getTotalPages(), "Es muss 1 Page vorhanden sein.");
		assertEquals(1, ergebnis.getTotalElements(), "Es muss 1 Buch insgesamt gefunden werden.");
		assertEquals(1, ergebnis.getNumberOfElements(), "Es muss 1 Buch auf der Page sein.");
	}

	@Test
	@DisplayName("Finde alle Bücher in Page. 10 Bücher vorhanden.")
	void findAllPage_10_Buecher () {
		for (int i = 0; i < 10; i++) {
			buchRepository.save(new Buch("Testbuch " + i, ISBN.fromString("978-1-2345-678-" + i)));
		}
		var page = PageRequest.of(0, 10);
		var ergebnis = buchRepository.findAll(page);

		assertEquals(1, ergebnis.getTotalPages(), "Es muss 1 Page vorhanden sein.");
		assertEquals(10, ergebnis.getTotalElements(), "Es muss 10 Bücher insgesamt gefunden werden.");
		assertEquals(10, ergebnis.getNumberOfElements(), "Es müssen 10 Bücher auf der Page sein.");

		page = PageRequest.of(0, 5);
		ergebnis = buchRepository.findAll(page);

		assertEquals(2, ergebnis.getTotalPages(), "Es muss 2 Pages vorhanden sein.");
		assertEquals(10, ergebnis.getTotalElements(), "Es muss 10 Bücher insgesamt gefunden werden.");
		assertEquals(5, ergebnis.getNumberOfElements(), "Es müssen 5 Bücher auf der Page sein.");
	}


	@Test
	@DisplayName("Gib Anzahl verschiedener Bücher zurück. Kein Buch vorhanden.")
	void countDistinctByIsbnNot_leer () {
		assertEquals(0, buchRepository.countDistinctBucherByIsbn());
	}

	@Test
	@DisplayName("Gib Anzahl verschiedener Bücher zurück. Nur 1 Buch vorhanden.")
	void countDistinctByIsbnNot_1_Buch () {
		buchRepository.save(DEFAULT_BUCH);
		assertEquals(1, buchRepository.countDistinctBucherByIsbn());
	}

	@Test
	@DisplayName("Gib Anzahl verschiedener Bücher zurück. 1 gleiches Buch immer.")
	void countDistinctByIsbnNot_1_Buch_alle_gleich () {
		for (int i = 0; i < 10; i++) {
			buchRepository.save(new Buch("Testbuch", ISBN_1));
		}

		var ergebnis = buchRepository.countDistinctBucherByIsbn();

		assertEquals(1, ergebnis, "Es darf nur 1 Buch geben.");
	}

	@Test
	@DisplayName("Gib Anzahl verschiedener Bücher zurück. Alles verschiedene Bücher.")
	void countDistinctByIsbnNot_alle_verschieden () {
		for (int i = 0; i < 10; i++) {
			buchRepository.save(new Buch("Testbuch " + i, ISBN.fromString("978-1-2345-678-" + i)));
		}

		var ergebnis = buchRepository.countDistinctBucherByIsbn();

		assertEquals(10, ergebnis, "Anzahl der verschiedenen Bücher ist nicht 10.");
	}

	@Test
	@DisplayName("Suche nach Autor. Kein Buch vom Autor vorhanden.")
	void findByAutorContains_leer () {
		var ergebnis = buchRepository.findByAutorContains("Testautor");
		assertEquals(0, ergebnis.toList().size(), "Es dürfen keine Bücher gefunden werden, wenn kein Buch vorhanden "
												  + "ist.");

		buchRepository.save(DEFAULT_BUCH);
		ergebnis = buchRepository.findByAutorContains("Testautor");
		assertEquals(0, ergebnis.toList().size(), "Es dürfen keine Bücher gefunden werden, wenn kein Buch vom Autor "
												  + "vorhanden ist.");

	}

	@Test
	@DisplayName("Suche nach Autor. 1 Buch vom Autor vorhanden.")
	void findByAutorContains_1_Buch () {
		buchRepository.save(DEFAULT_BUCH);

		var ergebnis = buchRepository.findByAutorContains("Testautor");

		assertEquals(0, ergebnis.toList().size(), "Es dürfen keine Bücher gefunden werden, wenn kein Buch vom Autor "
												  + "vorhanden ist.");

		var buch = new Buch("Testbuch", "Testautor", ISBN_1, null);
		buchRepository.save(buch);

		ergebnis = buchRepository.findByAutorContains("Testautor");

		assertEquals(1, ergebnis.toList().size(), "Es muss 1 Buch gefunden werden.");
	}

	@Test
	@DisplayName("Suche nach Autor. 10 Bücher vom Autor vorhanden.")
	void findByAutorContains_10_Buecher () {
		for (int i = 0; i < 10; i++) {
			buchRepository.save(new Buch("Testbuch " + i, "Testautor", ISBN.fromString("978-1-2345-678-" + i), null));
		}

		var ergebnis = buchRepository.findByAutorContains("Testautor");

		assertEquals(10, ergebnis.toList().size(), "Es müssen 10 Bücher gefunden werden.");
	}

	@Test
	@DisplayName("Suche nach Autor. 10 Bücher vom Autor vorhanden. 1 Buch vom anderen Autor vorhanden.")
	void findByAutorContains_10_Buecher_1_Buch () {
		for (int i = 0; i < 10; i++) {
			buchRepository.save(new Buch("Testbuch " + i, "Testautor", ISBN.fromString("978-1-2345-678-" + i), null));
		}
		buchRepository.save(new Buch("Testbuch", "Anderer Autor", ISBN_2, null));

		var ergebnis = buchRepository.findByAutorContains("Testautor");

		assertEquals(10, ergebnis.toList().size(), "Es müssen 10 Bücher gefunden werden. Das 11. ist nicht vom "
												   + "gesuchten Autor.");
	}

	@Test
	@DisplayName("Suche nach Autor. 10 Bücher vom Autor vorhanden. 1 Buch vom anderen Autor vorhanden.")
	void findByAutorContains_10_Buecher_1_Buch_2 () {
		for (int i = 0; i < 10; i++) {
			buchRepository.save(new Buch("Testbuch " + i, "Testautor", ISBN.fromString("978-1-2345-678-" + i), null));
		}
		buchRepository.save(new Buch("Testbuch", "Anderer Autor", ISBN_2, null));

		var ergebnis = buchRepository.findByAutorContains("Anderer Autor");
		assertEquals(1, ergebnis.toList().size(), "Es muss 1 Buch gefunden werden. Das 11. ist nicht vom "
												  + "gesuchten Autor.");
	}
}