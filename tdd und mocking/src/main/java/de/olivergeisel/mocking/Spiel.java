package de.olivergeisel.mocking;

public class Spiel {

	private Stapel  stapel;
	private Spieler geber;
	private Spieler hoerer;
	private Spieler sager;

	private Farben trumpf;

	private Spieler aktiverSpieler;

	public Spiel(Stapel stapel, Spieler geber, Spieler hoerer, Spieler sager) {
		this.stapel = stapel;
		this.geber = geber;
		this.hoerer = hoerer;
		this.sager = sager;
		aktiverSpieler = hoerer;
	}

	public void spielen() {
		// TODO Auto-generated method stub

	}

	private void init() {
		stapel.mischen();
	}

	public void setTrumpf(Farben trumpf) {
		this.trumpf = trumpf;
	}

	public Karte runde(Karte startKarte) {
		aktiverSpieler.ablegenKarte(startKarte);
		var karte1 = aktiverSpieler.bedienen(startKarte);
		var zweiterSpieler = naechsterSpieler(aktiverSpieler);
		var karte2 = zweiterSpieler.bedienen(startKarte);
		var dritterSpieler = naechsterSpieler(zweiterSpieler);
		var karte3 = dritterSpieler.bedienen(startKarte);
		return siegerKarte(karte1, karte2, karte3, trumpf);
	}

	private Karte siegerKarte(Karte karte1, Karte karte2, Karte karte3, Farben trumpf) {
		if (karte1.istBesser(karte2)) {
			if (karte1.istBesser(karte3)) {
				return karte1;
			} else {
				return karte3;
			}
		} else {
			if (karte2.istBesser(karte3)) {
				return karte2;
			} else {
				return karte3;
			}
		}
	}

	private Spieler naechsterSpieler(Spieler spieler) {
		if (spieler == geber)
			return hoerer;
		else if (spieler == hoerer)
			return sager;
		else if (spieler == sager)
			return geber;
		else
			throw new IllegalArgumentException("Unexpected value: " + spieler);
	}
}


