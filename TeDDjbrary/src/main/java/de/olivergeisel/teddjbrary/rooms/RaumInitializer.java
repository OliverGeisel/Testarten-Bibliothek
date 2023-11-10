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

package de.olivergeisel.teddjbrary.rooms;

import org.salespointframework.core.DataInitializer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RaumInitializer implements DataInitializer {

	private final RaumRepository<Raum> repo;

	public RaumInitializer (RaumRepository<Raum> repo) {this.repo = repo;}

	@Override
	public void initialize () {

		var raum1 = new Leseraum(42);
		raum1.setName("Raum der Wünsche");
		raum1.setBeschreibung("Hier kann man eine Armee für den Kampf gegen Voldemort aufstellen");

		var raum2 = new Leseraum(3);
		raum2.setName("Raum wo es passierte");
		raum2.setBeschreibung("Hier ist es passiert. Und Aaron Burr wäre gern in diesem Raum gewesen.");

		var raum3 = new Leseraum(1);
		raum3.setName("Festung der Einsamkeit");
		raum3.setBeschreibung("Für einsame Helden, die sich zurückziehen wollen.");

		var raum4 = new Leseraum(2);
		raum4.setName("Batcave");
		raum4.setBeschreibung("Wenn Batman ein Helferlein braucht, kann er hier jemanden anheuern.");

		var raum5 = new Leseraum(1);
		raum5.setName("Zimmer unter der Treppe");
		raum5.setBeschreibung("Hier wohnt Harry Potter, wenn er nicht in Hogwarts ist.");

		var raum6 = new Leseraum(100);
		raum6.setName("Die Minen von Moria");
		raum6.setBeschreibung("Am Türsteher kommt hier keiner vorbei.");

		var raum7 = new Leseraum(12);
		raum7.setName("Hohe Rat der Jedi");
		raum7.setBeschreibung("Hier wird über die Zukunft der Galaxis entschieden. Nur für Jedi-Meister. Und nun "
							  + "setzt euch junger Skywalker!");

		var raum8 = new Leseraum(10);
		raum8.setName("Ektoplasmatische Eindämmungseinheit");
		raum8.setBeschreibung("Könnte etwas eng werden. Außer man ist ein Geist.");

		var raum9 = new Leseraum(14);
		raum9.setName("Tardis");
		raum9.setBeschreibung("Von Innen viel größer als von Außen.\n\n allons-y");

		var werkstatt = new Werkstatt(null);
		var restauration = new Restaurationsraum("Aetherium Schmiede", 1, werkstatt);

		var alle = List.of(raum1, raum2, raum3, raum4, raum5, raum6, raum7, raum8, raum9, restauration);
		repo.saveAll(alle);
	}
}
