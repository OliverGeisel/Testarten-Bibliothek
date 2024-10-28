package de.olivergeisel.mocking;

import org.hibernate.mapping.Collection;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Stapel {

	private Deque<Karte> karten = new LinkedList<>();

	private int maxAnzahlKarten =32;


	public Stapel(){
		for (var farbe : Farben.values()) {
			for (var wert : Werte.values()) {
				karten.add(new Karte(farbe, wert));
			}
		}
	}

	public void mischen() {
		Collections.shuffle((List<?>) karten);
	}
}
