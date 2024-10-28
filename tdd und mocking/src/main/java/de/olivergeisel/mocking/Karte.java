package de.olivergeisel.mocking;

public class Karte implements Comparable<Karte>{

	private Werte wert;
	private Farben farbe;

	public Karte(Farben farbe, Werte wert) {
		this.farbe = farbe;
		this.wert = wert;
	}

	@Override
	public int compareTo(Karte o) {
		return wert.compareTo(o.wert);
	}

	public boolean istBesser(Karte o) {
		return wert.getWert()> o.wert.getWert();
	}


	public Farben getFarbe() {
		return farbe;
	}

	public Werte getWert() {
		return wert;
	}

	boolean isTrumpf(Farben trumpfFarbe) {
		return farbe == trumpfFarbe;
	}


}
