package de.olivergeisel.teddjbrary.user.staff;

import de.olivergeisel.teddjbrary.user.Geschlecht;
import jakarta.persistence.Entity;
import org.salespointframework.useraccount.UserAccount;

@Entity
public class Bibliothekar extends Angestellter {

	protected Bibliothekar() {
		super();
	}

	public Bibliothekar(UserAccount account, Geschlecht geschlecht, int alter) {
		super(account, geschlecht, alter);
	}
}

