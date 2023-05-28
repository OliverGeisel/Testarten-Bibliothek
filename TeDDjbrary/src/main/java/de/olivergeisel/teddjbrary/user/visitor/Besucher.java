package de.olivergeisel.teddjbrary.user.visitor;

import de.olivergeisel.teddjbrary.core.Buch;
import de.olivergeisel.teddjbrary.user.Person;
import de.olivergeisel.teddjbrary.structure.Terminal;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public abstract class Besucher implements Person {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private UUID id;

	private String vorname;
	private String nachname;

	protected Besucher() {

	}

	protected Besucher(String vorname, String nachname) {
		this.nachname = nachname;
		this.vorname = vorname;
	}

	/**
	 * @param buch
	 * @return
	 */
	abstract boolean ausleihen(Buch buch, Terminal terminal);

	/**
	 * @param buch
	 * @return
	 */
	abstract boolean zurueckgeben(Buch buch, Terminal terminal);

	/**
	 * @return true, wenn keine Probleme beim Bezahlen gab, sonst false
	 */
	abstract boolean bezahlen(Terminal terminal);

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Besucher besucher)) return false;

		return id.equals(besucher.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

//
	@Override
	public int getAlter() {
		return 0;
	}

	public UUID getId() {
		return id;
	}

	@Override
	public String getNachname() {
		return nachname;
	}

	@Override
	public String getVorname() {
		return vorname;
	}
//
}
