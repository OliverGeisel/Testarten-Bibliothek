package de.olivergeisel.mocking;

import java.util.ArrayList;
import java.util.List;

public class Spieler {

	private List<Karte> hand;

	public Spieler() {
	hand=new ArrayList<>();
	}

	public Spieler(List<Karte> hand) {
		this.hand = hand;
	}

	public List<Karte> getHand() {
		return hand;
	}

	public Karte ablegenKarte(Karte karte) {
		hand.remove(karte);
		return karte;
	}

	public Karte bedienen (Karte karte) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public Karte tauschen(Karte nehmen, Karte ablegen) {
		hand.remove(ablegen);
		hand.add(nehmen);
		return ablegen;
	}


}
