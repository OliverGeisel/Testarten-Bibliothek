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

import de.olivergeisel.teddjbrary.core.Verschmutzbar;
import de.olivergeisel.teddjbrary.user.visitor.Besucher;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Leseraum extends Raum implements Verschmutzbar {

	@OneToMany(cascade = CascadeType.ALL)
	private final List<Besucher> leser       = new LinkedList<>();
	private       int            maxPersonen = 5;
	private       boolean        besetzt;
	private       double         verschmutzung;
	private       int            imRaum;

	protected Leseraum () {
	}

	public Leseraum(int leserSitze) {
		if (leserSitze < 1) {
			throw new IllegalArgumentException("Es muss mindestens eine Person in den Raum passen");
		}
		maxPersonen = leserSitze;
	}

	public Leseraum (String name, int nummer, int leserSitze) {
		super(name, nummer);
		if (leserSitze < 1) {
			throw new IllegalArgumentException("Es muss mindestens eine Person in den Raum passen");
		}
		maxPersonen = leserSitze;
	}


	public Besucher[] betreten(Besucher... besucher) {
		int i = 0;
		List<Besucher> back = new LinkedList<>();
		for (Besucher b : besucher) {
			if (leser.contains(b)) {
				continue;
			}
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

	@Override
	public void saeubern() {
		verschmutzung = 0.0;
	}

	@Override
	public void verschmutzen() {
		verschmutzung += 0.1;
	}

	//region setter/getter
	public List<Besucher> getPersonenImRaum() {
		return Collections.unmodifiableList(leser);
	}

	public boolean isBesetzt() {
		return besetzt;
	}

	public boolean isVoll () {
		return leser.size() >= maxPersonen;
	}

	@Override
	public boolean isDreckig() {
		return verschmutzung >= 0.75;
	}

	public int getImRaum() {
		return imRaum;
	}

	public int getMaxPersonen () {
		return maxPersonen;
	}
//endregion
}
