package de.olivergeisel.teddjbrary.structure;

import de.olivergeisel.teddjbrary.core.Buch;
import de.olivergeisel.teddjbrary.core.ISBN;
import de.olivergeisel.teddjbrary.core.Verschmutzbar;
import de.olivergeisel.teddjbrary.inventory.RegalCode;
import de.olivergeisel.teddjbrary.user.visitor.Besucher;
import de.olivergeisel.teddjbrary.rooms.Leseraum;

import java.util.Collection;

/**
 * Ein Terminal ist eine Schnittstelle der Bibliothek, die die Services der Bibliothek zugänglich macht.
 */
public interface Terminal extends Verschmutzbar {
	Buch sucheNachISBN(ISBN isbn);

	Collection<Buch> sucheNachAuthor(String author);

	Buch sucheNachTitel(String titel);

	Buch sucheNachTreffer(String text);

	boolean ausleihen(Buch buch, Besucher besucher);

	boolean zurueckgeben(Buch buch);

	RegalCode findeRegalCode(Buch buch);

	double getMahngebuehren(Besucher besucher);

	Leseraum reservieren(Besucher... besucher);

	boolean bezahlen(Besucher besucher, double betrag);
}