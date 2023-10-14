package de.olivergeisel.teddjbrary.user.staff;

import de.olivergeisel.teddjbrary.user.Geschlecht;
import jakarta.persistence.Entity;
import org.salespointframework.useraccount.UserAccount;

@Entity
public class Restaurator extends Angestellter {

	protected Restaurator() {
		super();
	}

	public Restaurator(UserAccount userAccount, Geschlecht geschlecht, int alter) throws IllegalArgumentException {
		super(userAccount, geschlecht, alter);
	}
}
