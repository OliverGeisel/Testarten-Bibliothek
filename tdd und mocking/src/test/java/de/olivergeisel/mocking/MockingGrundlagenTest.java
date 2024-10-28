package de.olivergeisel.mocking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Tag("mocking")
@Tag("grundlagen")
@Tag("unit")
public class MockingGrundlagenTest {

	private Spiel spiel;

	@BeforeEach
	void setUp() {
		var stapel = mock(Stapel.class);
		spiel = new Spiel(stapel, new Spieler(), new Spieler(), new Spieler());
	}

}


@Tag("mocking")
@Tag("grundlagen")
@Tag("unit")
@ExtendWith(MockitoExtension.class) // JUnit 5
class MockingGrundlagenAnnotationenTest{
	private Spiel spiel;
	@Mock
	private Stapel stapel;
	@Mock
	private Spieler geber;
	@Mock
	private Spieler hoerer;
	@Mock
	private Spieler sager;


	@BeforeEach
	void setUp() {
		spiel = new Spiel(stapel, geber, hoerer, sager);
	}

	@Test
	void testBedienenOhneStich() {
		var startKarte = new Karte(Farben.HERZ, Werte.SIEBEN);
		var zweiteKarte = new Karte(Farben.HERZ, Werte.ACHT);
		var dritteKarte = new Karte(Farben.HERZ, Werte.UNTER);
		when(hoerer.bedienen(startKarte)).thenReturn(startKarte);
		when(sager.bedienen(startKarte)).thenReturn(zweiteKarte);
		when(geber.bedienen(startKarte)).thenReturn(dritteKarte);

		var ergebnis = spiel.runde(startKarte);

		assertEquals(dritteKarte, ergebnis);
		verify(hoerer,atLeastOnce()).ablegenKarte(startKarte);
		verify(sager,atLeastOnce()).bedienen(startKarte);
		verify(geber,atLeastOnce()).bedienen(startKarte);

	}

}