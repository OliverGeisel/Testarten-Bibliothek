package de.olivergeisel.teddjbrary.user.staff;

import de.olivergeisel.teddjbrary.user.Geschlecht;
import de.olivergeisel.teddjbrary.user.Person;
import jakarta.persistence.*;
import org.salespointframework.useraccount.UserAccount;

import java.util.UUID;

@Entity
public abstract class Angestellter implements Person {
	private final Geschlecht  geschlecht;
	private       String      vorname;
	private       String      nachname;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private       UUID        id;
	@OneToOne(cascade = CascadeType.ALL)
	private       UserAccount userAccount;
	private       int         alter;

	protected Angestellter() {
		this("", "", Geschlecht.DIVERS, 0);
	}

	protected Angestellter(UserAccount userAccount, Geschlecht geschlecht, int alter) {
		this(userAccount.getFirstname(), userAccount.getLastname(), geschlecht, alter);
		this.userAccount = userAccount;
	}

	protected Angestellter(String name, String nachname, Geschlecht geschlecht, int alter) {
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
	public UUID getId() {
		return id;
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

	public Geschlecht getGeschlecht() {
		return geschlecht;
	}

	public String getNachname() {
		return nachname;
	}

	public String getVollerName() {
		return String.format("%s %s", vorname, nachname);
	}
//endregion
}


