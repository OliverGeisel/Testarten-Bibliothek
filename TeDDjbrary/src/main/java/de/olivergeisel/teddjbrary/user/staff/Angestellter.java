package de.olivergeisel.teddjbrary.user.staff;

import de.olivergeisel.teddjbrary.user.Geschlecht;
import de.olivergeisel.teddjbrary.user.Person;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public abstract class Angestellter implements Person {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private UUID id;

	private int alter;
	private final String vorname;
	private final String nachname;
	private final Geschlecht geschlecht;

	protected Angestellter() {
		this("", "", Geschlecht.DIVERS, 0);
	}

	public UUID getId() {
		return id;
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
}


