package de.olivergeisel.teddjbrary.user.visitor;

import de.olivergeisel.teddjbrary.core.Buch;
import de.olivergeisel.teddjbrary.structure.Terminal;
import jakarta.persistence.Entity;

import java.util.UUID;

@Entity
public class Dozent extends Besucher {

	public Dozent(UUID id, String vorname, String nachname) {
		super( vorname,nachname);
	}

	public Dozent() {

	}

	@Override
	public boolean ausleihen(Buch buch, Terminal terminal) {
		return terminal.ausleihen(buch,this);
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
