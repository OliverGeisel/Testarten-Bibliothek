package de.olivergeisel.teddjbrary.user.visitor;

import de.olivergeisel.teddjbrary.core.Buch;
import de.olivergeisel.teddjbrary.structure.Terminal;
import jakarta.persistence.Entity;
import org.salespointframework.useraccount.UserAccount;

@Entity
public class Dozent extends Besucher {

	public Dozent(String vorname, String nachname, UserAccount userAccount) {
		super(vorname, nachname, userAccount);
	}

	public Dozent(UserAccount userAccount) {
		super(userAccount.getFirstname(), userAccount.getLastname(), userAccount);
	}

	public Dozent() {

	}

	@Override
	public boolean ausleihen(Buch buch, Terminal terminal) {
        return terminal.ausleihen(buch, this);
	}

	@Override
	public boolean zurueckgeben(Buch buch, Terminal terminal) {
		return terminal.zurueckgeben(buch);
	}

	@Override
	public boolean bezahlen(Terminal terminal) {
		// todo implement
		return true;
	}
}
