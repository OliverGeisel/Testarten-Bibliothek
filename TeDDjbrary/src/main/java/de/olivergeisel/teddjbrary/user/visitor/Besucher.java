package de.olivergeisel.teddjbrary.user.visitor;

import de.olivergeisel.teddjbrary.core.Buch;
import de.olivergeisel.teddjbrary.structure.Terminal;
import de.olivergeisel.teddjbrary.user.Benutzer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import org.salespointframework.useraccount.UserAccount;

@Entity
public abstract class Besucher extends Benutzer {

	private String vorname;
	private String nachname;

	@OneToOne(optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
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


	@Override
	public String getNachname() {
		return nachname;
	}

	@Override
	public String getVorname() {
		return vorname;
	}

//endregion


}
