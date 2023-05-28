package de.olivergeisel.teddjbrary.rooms;

import de.olivergeisel.teddjbrary.core.Verschmutzbar;
import de.olivergeisel.teddjbrary.user.visitor.Besucher;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Entity
public class Leseraum implements Verschmutzbar {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private UUID id;

	@OneToMany
	private final List<Besucher> leser;
	private int maxPersonen;
	private boolean besetzt;
	private double verschmutzung;
	private int imRaum;

	public Leseraum() {
		leser = new LinkedList<>();
	}

	public UUID getId() {
		return id;
	}

	public Leseraum(int leserSitze) {
		this();
		if (leserSitze < 1) {
			throw new IllegalArgumentException("Es muss mindestens eine Person in den Raum passen");
		}
		maxPersonen=leserSitze;
	}

	public Besucher[] betreten(Besucher... besucher) {
		int i = 0;
		List<Besucher> back = new LinkedList<>();
		for (Besucher b : besucher) {
			leser.add(b);
			back.add(b);
			i++;
		}
		besetzt = true;
		return back.toArray(new Besucher[1]);
	}

	public List<Besucher> verlassen() {
		besetzt = false;
		List<Besucher> back = new LinkedList<>(leser);
		for (int i = 0; i < leser.size(); i++) {
			verschmutzen();
		}
		leser.clear();
		return back;
	}

	public List<Besucher> getPersonenImRaum() {
		return Collections.unmodifiableList(leser);
	}

	public boolean isBesetzt() {
		return besetzt;
	}

	@Override
	public boolean isDreckig() {
		return verschmutzung >= 0.75;
	}

	@Override
	public void saeubern() {
		verschmutzung = 0.0;
	}

	@Override
	public void verschmutzen() {
		verschmutzung += 0.1;
	}
}
