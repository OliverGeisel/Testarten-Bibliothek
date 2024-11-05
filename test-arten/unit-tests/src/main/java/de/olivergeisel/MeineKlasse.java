package de.olivergeisel;

public class MeineKlasse {

	private AndereKlasse andereKlasse;

	public MeineKlasse(AndereKlasse mockObjekt) {

	}

	public void m1() {}

	public void methodeMitException() {
		throw new UnsupportedOperationException("Methode nicht implementiert");
	}

//region setter/getter
	public boolean isGut() {
		return false;
	}

	public boolean isX() {
		return false;
	}

	public AndereKlasse getAndereKlasse() {
		return andereKlasse;
	}
//endregion
}
