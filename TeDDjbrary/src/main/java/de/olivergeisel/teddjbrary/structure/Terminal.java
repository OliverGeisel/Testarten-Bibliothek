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
import de.olivergeisel.teddjbrary.core.Verschmutzbar;
import de.olivergeisel.teddjbrary.inventory.RegalCode;
import de.olivergeisel.teddjbrary.rooms.Leseraum;
import de.olivergeisel.teddjbrary.user.visitor.Besucher;

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