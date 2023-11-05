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

import de.olivergeisel.teddjbrary.user.Benutzer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
public abstract class Arbeitsraum<P extends Benutzer> extends Raum {

	@OneToMany(targetEntity = Arbeitsplatz.class, cascade = CascadeType.ALL)
	private Set<Arbeitsplatz<P>> plaetze;

	@SafeVarargs
	protected Arbeitsraum (String name, int nummer, final Arbeitsplatz<P>... plaetze) {
		super(name, nummer);
		this.plaetze = new HashSet<>();
		for (var platz : plaetze) {
			this.plaetze.add(platz);
		}
	}

	protected Arbeitsraum () {

	}
}
