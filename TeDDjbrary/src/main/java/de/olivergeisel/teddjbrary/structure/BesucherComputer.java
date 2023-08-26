package de.olivergeisel.teddjbrary.structure;

import de.olivergeisel.teddjbrary.core.Buch;
import de.olivergeisel.teddjbrary.core.ISBN;
import de.olivergeisel.teddjbrary.inventory.BestandsVerwaltung;
import de.olivergeisel.teddjbrary.inventory.RegalCode;
import de.olivergeisel.teddjbrary.rooms.Arbeitsplatz;
import de.olivergeisel.teddjbrary.rooms.Leseraum;
import de.olivergeisel.teddjbrary.user.staff.VerwaltungsException;
import de.olivergeisel.teddjbrary.user.visitor.Besucher;

import java.util.Collection;

// Anzahl Bugs:
public class BesucherComputer extends Arbeitsplatz<Besucher> implements Terminal {
	private static int                counter = 1;
	private final  int                number;
	private final  BestandsVerwaltung bestand;

	public BesucherComputer(BestandsVerwaltung bestand) {
		this.bestand = bestand;
		number = counter;
		counter++;
	}

	@Override
	public Buch sucheNachISBN(ISBN isbn) {
		return bestand.sucheNachISBN(isbn);
	}

	@Override
	public Collection<Buch> sucheNachAuthor(String author) {
		return bestand.sucheNachAuthor(author);
	}

	@Override
	public Buch sucheNachTitel(String titel) {
		return bestand.sucheNachTitel(titel);
	}

	@Override
	public Buch sucheNachTreffer(String text) {
		return bestand.sucheNachTreffer(text);
	}

	@Override
	public boolean ausleihen(Buch buch, Besucher besucher) throws VerwaltungsException {
		if (!besucher.equals(getNutzer()))
			throw new VerwaltungsException("Du kannst kein Buch für jemand anderen ausleihen");
		ausleihen(buch);
		return false;
	}

	public boolean ausleihen(Buch buch) {
		if (!isBesetzt()) {
			return false;
		}
		return bestand.ausleihen(buch, getNutzer());
	}

	public boolean zurueckgeben(Buch buch) {
		if (!isBesetzt()) {
			return false;
		}
		return bestand.zurueckgeben(buch);

	}

	@Override
	public RegalCode findeRegalCode(Buch buch) {
		return bestand.getRegalCode(buch);
	}

	@Override
	public double getMahngebuehren(Besucher besucher) {

		throw new UnsupportedOperationException("Diese Art Terminals unterstützt diese Funktionalität nicht!");
	}

	@Override
	public Leseraum reservieren(Besucher... besucher) {
		throw new UnsupportedOperationException("Diese Art Terminals unterstützt diese Funktionalität nicht!");
	}

	@Override
	public boolean bezahlen(Besucher besucher, double betrag) {
		throw new UnsupportedOperationException("Diese Art Terminals unterstützt diese Funktionalität nicht!");
	}

	@Override
	public void verschmutzen() {
		verschmutzung += 0.1;
	}

	public String toString() {
		// VLT. besser einen StringBuilder
		return "Das ist Terminal " + number + ". Und es ist mit " + (nutzer != null ? "niemanden" : nutzer)
			   + " bestzt.";
	}
}
