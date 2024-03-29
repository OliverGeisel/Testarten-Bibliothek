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

import de.olivergeisel.teddjbrary.user.staff.Verwaltung;
import jakarta.persistence.Entity;

@Entity
public class Buero extends Arbeitsraum<Verwaltung> {
	public Buero (String name, int nummer, Arbeitsplatz... plaetze) {
		super(name, nummer, plaetze);
	}

	@SuppressWarnings("unused")
	protected Buero () {

	}
}

@Entity
class Verwaltungsplatz extends Arbeitsplatz<Verwaltung> {
	public Verwaltungsplatz () {
		super();
	}

	/**
	 * Eine Methode, die das Objekt zu einem gewissen Anteil verschmutzt. Nach einer gewissen Verschmutzung muss isDreckig()
	 * true zurückgeben.
	 */
	@Override
	public void verschmutzen () {
		verschmutzung += 0.1;
	}
}
