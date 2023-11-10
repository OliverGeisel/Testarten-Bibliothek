/*
 * Copyright 2023 Oliver Geisel
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package de.olivergeisel.teddjbrary.structure;

import de.olivergeisel.teddjbrary.core.Buch;
import de.olivergeisel.teddjbrary.core.ISBN;
import de.olivergeisel.teddjbrary.inventory.BestandsVerwaltung;
import de.olivergeisel.teddjbrary.inventory.RegalCode;
import de.olivergeisel.teddjbrary.rooms.Arbeitsplatz;
import de.olivergeisel.teddjbrary.rooms.Leseraum;
import de.olivergeisel.teddjbrary.user.staff.Bibliothekar;
import de.olivergeisel.teddjbrary.user.visitor.Besucher;
import de.olivergeisel.teddjbrary.user.visitor.Kundenregister;

import java.util.Collection;

public class AngestelltenComputer extends Arbeitsplatz<Bibliothekar> implements Terminal {

	private final Kundenregister     kunden;
	private final BestandsVerwaltung bestand;

	private final Leseraum[] raeume;

	public AngestelltenComputer(Kundenregister kunden, BestandsVerwaltung bestand, Leseraum[] raeume) {
		this.kunden = kunden;
		this.bestand = bestand;
		this.raeume = raeume;
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
	public boolean ausleihen(Buch buch, Besucher besucher) {
		return bestand.ausleihen(buch, besucher);
	}

	@Override
	public boolean zurueckgeben(Buch buch) {
		return bestand.zurueckgeben(buch);
	}

	@Override
	public RegalCode findeRegalCode(Buch buch) {
		return bestand.getRegalCode(buch);
	}


	@Override
	public double getMahngebuehren(Besucher besucher) {
		return kunden.getStrafe(besucher);
	}

	@Override
	public Leseraum reservieren(Besucher... besucher) {

		// todo  fix
		return raeume[0];
	}

	@Override
	public boolean bezahlen(Besucher besucher, double betrag) {
		return kunden.bezahlen(besucher);
	}

	public boolean besucherHinzufuegen(Besucher besucher) {
		return kunden.addBesucher(besucher);
	}

	@Override
	public void verschmutzen() {
		verschmutzung += 0.02;
	}
}
