package de.olivergeisel.teddjbrary.user.visitor;

import de.olivergeisel.teddjbrary.core.Buch;
import de.olivergeisel.teddjbrary.structure.Terminal;
import jakarta.persistence.Entity;

@Entity
public class Studierender extends Besucher {
    public Studierender(String vorname, String nachname) {
        super(vorname, nachname);
    }

    public Studierender() {

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
		// Todo implement
		return true;
	}
}
