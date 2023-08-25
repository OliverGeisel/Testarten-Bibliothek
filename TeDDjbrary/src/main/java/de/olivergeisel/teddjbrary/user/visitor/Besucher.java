package de.olivergeisel.teddjbrary.user.visitor;

import de.olivergeisel.teddjbrary.core.Buch;
import de.olivergeisel.teddjbrary.structure.Terminal;
import de.olivergeisel.teddjbrary.user.Person;
import jakarta.persistence.*;
import org.salespointframework.useraccount.UserAccount;

import java.util.UUID;

@Entity
public abstract class Besucher implements Person {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private UUID id;

	private String vorname;
	private String nachname;

	@OneToOne(cascade = CascadeType.ALL)
	private UserAccount userAccount;

	protected Besucher() {

	}

	protected Besucher(String vorname, String nachname, UserAccount userAccount) {
		this.nachname = nachname;
		this.vorname = vorname;
		this.userAccount = userAccount;
	}

	/**
	 * Lässt den Besucher ein Buch ausleihen
	 *
	 * @param terminal das Terminal, an dem das Buch ausgeliehen werden soll
	 * @param buch     das Buch, das ausgeliehen werden soll
	 * @return true, wenn keine Probleme beim Ausleihen gab, sonst false
	 */
	abstract boolean ausleihen(Buch buch, Terminal terminal);

	/**
	 * Ein Besucher gibt ein Buch zurück.
	 *
	 * @param terminal das Terminal, an dem das Buch zurückgegeben werden soll
	 * @param buch     das Buch, das zurückgegeben werden soll
	 * @return true, wenn keine Probleme beim Zurückgeben gab, sonst false
	 */
	abstract boolean zurueckgeben(Buch buch, Terminal terminal);

	/**
	 * @return true, wenn keine Probleme beim Bezahlen gab, sonst false
	 */
	abstract boolean bezahlen(Terminal terminal);

	//region setter/getter
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

	public UserAccount getUserAccount() {
		return userAccount;
	}
//endregion

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

}
