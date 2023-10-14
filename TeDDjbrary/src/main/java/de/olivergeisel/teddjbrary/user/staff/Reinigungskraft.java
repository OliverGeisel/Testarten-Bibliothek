package de.olivergeisel.teddjbrary.user.staff;

import de.olivergeisel.teddjbrary.core.Buch;
import de.olivergeisel.teddjbrary.core.Verschmutzbar;
import de.olivergeisel.teddjbrary.inventory.Regal;
import de.olivergeisel.teddjbrary.user.Geschlecht;
import jakarta.persistence.Entity;
import org.salespointframework.useraccount.UserAccount;

@Entity
public class Reinigungskraft extends Angestellter {

	protected Reinigungskraft() {
		super();
	}

	public Reinigungskraft(UserAccount userAccount, Geschlecht geschlecht, int alter) throws IllegalArgumentException {
		super(userAccount, geschlecht, alter);
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
