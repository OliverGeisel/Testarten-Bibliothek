package de.olivergeisel.teddjbrary.user.visitor;

import de.olivergeisel.teddjbrary.core.Buch;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Klasse die den internen Status eines Besuchers repräsentiert.
 * Das beinhaltet z.B. Strafen, ausgeliehene Bücher, etc.
 */
@Entity
public class BesucherStatus {
	private final BesucherTyp besucherTyp;
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", nullable = false)
	private UUID id;
	@OneToOne
	private Besucher besucher;
	@ElementCollection
	private List<UUID> buchIds = new LinkedList<>();
	private       double      strafe;

	protected BesucherStatus() {
		besucherTyp = BesucherTyp.Normal;
	}

	BesucherStatus(Besucher besucher) {
		this.besucher = besucher;
		besucherTyp =
				switch (besucher) {
					case Dozent d -> BesucherTyp.Dozent;
					case Studierender ignored -> BesucherTyp.Studierender;
					default -> BesucherTyp.Normal;
				};
	}

	/**
	 * Bezahlt die Strafe und setzt sie wieder auf 0.
	 */
	void bezahlen() {
		this.strafe = 0.0;
	}

	boolean registriereAusgeliehenesBuch(Buch buch) {
		return buchIds.add(buch.getId());
	}

	/**
	 * Erhöht die Strafe um den angegebenen Wert.
	 *
	 * @param kosten Betrag, um den die Strafe erhöht wird.
	 * @return neuer Betrag der Strafe.
	 */
	public Double erhoeheStrafe(Double kosten) {
		this.strafe += kosten;
		return strafe;
	}

	public boolean entferneBuchAusAusgelieheneBuecher(Buch buch) {
		return buchIds.remove(buch.getId());
	}

	//region setter/getter
	public UUID getId() {return id;}

	public void setId(UUID id) {this.id = id;}

	public Besucher getBesucher() {
		return besucher;
	}

	public void setBesucher(Besucher besucher) {
		this.besucher = besucher;
	}

	public List<UUID> getAusgelieheneBuecher() {
		return Collections.unmodifiableList(buchIds);
	}

	public BesucherTyp getBesucherTyp() {
		return besucherTyp;
	}

	public Double getStrafe() {
		return strafe;
	}
}
