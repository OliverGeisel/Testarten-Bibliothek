package de.olivergeisel.teddjbrary.user.staff;

import de.olivergeisel.teddjbrary.user.Benutzer;
import de.olivergeisel.teddjbrary.user.Geschlecht;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import org.salespointframework.useraccount.UserAccount;

@Entity
public abstract class Angestellter extends Benutzer {

	private final Geschlecht geschlecht;
	private       String     vorname;
	private       String     nachname;
	private       int        alter;
	@Enumerated
	private       Bereich    bereich;

	protected Angestellter() {
		this.geschlecht = Geschlecht.DIVERS;
		this.vorname = "";
		this.nachname = "";
		this.alter = 0;
	}

	/**
	 * Erstellt einen neuen Angestellten mit den übergebenen Parametern.
	 *
	 * @param userAccount Der UserAccount des Angestellten (darf nicht null sein) und muss einen Namen und Vornamen
	 *                    haben.
	 * @param geschlecht  Das Geschlecht des Angestellten.
	 * @param alter       Das Alter des Angestellten. Muss mindestens 18 sein.
	 * @throws IllegalArgumentException Wenn der UserAccount null ist, oder der Vor-/Nachname leer ist, oder das Alter
	 *                                  kleiner als 18 ist.
	 */
	protected Angestellter(UserAccount userAccount, Geschlecht geschlecht, int alter) throws IllegalArgumentException {
		this(userAccount, userAccount.getFirstname(), userAccount.getLastname(), geschlecht, alter);
	}

	protected Angestellter(UserAccount userAccount, String name, String nachname, Geschlecht geschlecht, int alter)
			throws IllegalArgumentException {
		super(userAccount);
		if (name == null || nachname == null || alter < 18) {
			throw new IllegalArgumentException("Ungültige Namen bzw. Alter!");
		}
		if (name.isBlank() || nachname.isBlank()) {
			throw new IllegalArgumentException("Der Vor-/Name darf nicht leer sein");
		}
		this.vorname = name;
		this.geschlecht = geschlecht;
		this.alter = alter;
		this.nachname = nachname;
	}

	//region setter/getter
	public Bereich getBereich() {
		return bereich;
	}

	public void setBereich(Bereich bereich) {
		this.bereich = bereich;
	}

	public int getAlter() {
		return alter;
	}

	public void setAlter(int alter) {
		if (alter < 18) {
			throw new IllegalArgumentException("Zu Jung um angestellt zu werden.");
		}
		this.alter = alter;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public Geschlecht getGeschlecht() {
		return geschlecht;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getVollerName() {
		return String.format("%s %s", vorname, nachname);
	}

//endregion
}


