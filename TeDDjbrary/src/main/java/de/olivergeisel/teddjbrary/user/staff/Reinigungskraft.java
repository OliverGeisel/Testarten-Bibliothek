package de.olivergeisel.teddjbrary.user.staff;

import de.olivergeisel.teddjbrary.core.Buch;
import de.olivergeisel.teddjbrary.core.Verschmutzbar;
import de.olivergeisel.teddjbrary.inventory.Regal;
import de.olivergeisel.teddjbrary.user.Geschlecht;

public class Reinigungskraft extends Angestellter {


	public Reinigungskraft(String vorname, String nachname, Geschlecht geschlecht, int alter) {
		super(vorname, nachname, geschlecht, alter);
	}

	public boolean saeubern(Verschmutzbar gegenstand) {
		if (gegenstand instanceof Regal regal) {
			for (Buch buch : regal.alleBuecher()) {
				buch.saeubern();
			}
		}
		gegenstand.saeubern();
		return true;
	}


}
