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

package de.olivergeisel.teddjbrary.inventory;


import de.olivergeisel.teddjbrary.core.Buch;
import de.olivergeisel.teddjbrary.core.Verschmutzbar;
import de.olivergeisel.teddjbrary.structure.NoMatchingBookException;
import jakarta.persistence.*;

import java.util.*;
import java.util.function.Consumer;

/**
 * Beinhaltet Bücher. Diese Bücher sind in auf Brettern platziert.
 */
@Entity
public class Regal implements Verschmutzbar, Iterable<Buch> {
	public static final int              REGALBRETTER_DEFAULT     = 5;
	public static final int              BUECHER_JE_BRETT_DEFAULT = 20;
	public final        int              kapazitaet;
	@OneToMany(cascade = CascadeType.ALL)
	private final       List<RegalBrett> inhalt;
	@Embedded
	private final       RegalCode        code;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private             UUID             id;
	private             double           verschmutzung;

	protected Regal () {
		Random newCode = new Random();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 3; i++) {
			char randomChar = (char) (newCode.nextInt(26) + 'A');
			sb.append(randomChar);
		}
		this.code = new RegalCode(sb.toString(), newCode.nextInt(10_000, 99_999));
		this.inhalt = new ArrayList<>(REGALBRETTER_DEFAULT);
		for (int i = 0; i < REGALBRETTER_DEFAULT; ++i) {
			this.inhalt.add(new RegalBrett());
		}
		kapazitaet = REGALBRETTER_DEFAULT * BUECHER_JE_BRETT_DEFAULT;
	}

	public Regal (int regalBretter, int buecherJeBrett, RegalCode code) {
		this.code = code;
		this.inhalt = new ArrayList<>(REGALBRETTER_DEFAULT);
		for (int i = 0; i < REGALBRETTER_DEFAULT; ++i) {
			this.inhalt.add(new RegalBrett());
		}
		kapazitaet = regalBretter * buecherJeBrett;
	}

	public long anzahlBuecherImRegal () {
		return inhalt.stream().mapToLong(it -> it.getBuecher().stream().filter(Objects::nonNull).count()).sum();
	}

	public List<Buch> alleBuecher () {
		List<Buch> back = new LinkedList<>();
		for (RegalBrett regal : inhalt) {
			back.addAll(regal.getBuecher().stream().filter(Objects::nonNull).toList());
		}
		return back;
	}

	public Buch hineinStellen (Buch buch) throws IllegalStateException {
		if (isVoll()) {
			throw new IllegalStateException("Volle Regale können nicht befüllt werden");
		}
		RegalSchleife:
		for (RegalBrett brett : inhalt) {
			ReihenSchleife:
			for (int i = 0; i < brett.size(); ++i) {
				if (brett.get(i) == null) {
					brett.hineinStellen(buch);
					break RegalSchleife;
				}
			}
		}
		// Simuliert verschmutung
		verschmutzen();
		return buch;
	}

	public boolean enthaelt (Buch buch) {
		return alleBuecher().contains(buch);
	}

	public Buch herausnehmen (Buch buch) throws NoMatchingBookException {
		if (!enthaelt(buch)) {
			throw new NoMatchingBookException();
		}
		for (RegalBrett momentaneReihe : inhalt) {
			for (int buchIndex = 0; buchIndex < momentaneReihe.size(); buchIndex++) {
				Buch momentanesBuch = momentaneReihe.get(buchIndex);
				if (buch.equals(momentanesBuch)) {
					momentaneReihe.entfernen(buchIndex);
					break;
				}
			}
		}
		return buch;
	}

	public List<Buch> leeren () {
		List<Buch> back = alleBuecher();
		for (RegalBrett brett : inhalt) {
			brett.leeren();
		}
		return back;
	}

	@Override
	public void saeubern () {
		verschmutzung = 0.0;
	}

	@Override
	public void verschmutzen () {
		verschmutzung += 0.03;
	}

	@Override
	public Iterator<Buch> iterator () {
		return alleBuecher().iterator();
	}

	//region setter/getter

	public RegalCode getCode () {
		return code;
	}

	public UUID getId () {
		return id;
	}

	@Override
	public boolean isDreckig () {
		return verschmutzung > 0.5;
	}

	public boolean isVoll () {
		for (RegalBrett brett : inhalt) {
			if (!brett.isVoll()) {
				return false;
			}
		}
		return true;
	}
//endregion

	@Override
	public String toString () {
		return "Das ist Regal: " + code;
	}

}

@Entity
class RegalBrett implements Iterable<Buch> {
	private final int        kapazitaet;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private       UUID       id;
	@OneToMany
	private       List<Buch> buecher;

	public RegalBrett () {
		this(Regal.BUECHER_JE_BRETT_DEFAULT);
	}

	public RegalBrett (int buecher) {
		this.buecher = new ArrayList<>(buecher);
		for (int i = 0; i < buecher; ++i) {
			this.buecher.add(null);
		}
		kapazitaet = buecher;
	}

	public void leeren () {
		buecher.clear();
	}

	/**
	 * Stellt ein Buch an die nächste freie Stelle in das Regalbrett. Wenn das Regalbrett voll ist, wird eine
	 * {@link IllegalStateException} geworfen.
	 *
	 * @param buch Buch, das in das Regalbrett gestellt werden soll.
	 * @throws IllegalStateException wenn das Regalbrett voll ist.
	 */
	public void hineinStellen (Buch buch) throws IllegalStateException {
		if (isVoll()) {
			throw new IllegalStateException("Volle Regalbretter können nicht befüllt werden");
		}
		for (int i = 0; i < buecher.size(); ++i) {
			if (buecher.get(i) == null) {
				buecher.set(i, buch);
				break;
			}
		}
	}

	/**
	 * Stellt ein Buch an die angegebene Stelle in das Regalbrett. Wenn das Regalbrett voll ist oder an der gewählten
	 * Stelle bereits ein Buch ist, wird eine {@link IllegalStateException} geworfen.
	 *
	 * @param buch      Buch, das in das Regalbrett gestellt werden soll.
	 * @param buchIndex Index, an dem das Buch in das Regalbrett gestellt werden soll.
	 * @throws IllegalStateException wenn das Regalbrett voll ist oder an der gewählten Stelle bereits ein Buch ist.
	 */
	public void hineinStellen (Buch buch, int buchIndex) throws IllegalStateException {
		if (isVoll()) {
			throw new IllegalStateException("Volle Regalbretter können nicht befüllt werden");
		}
		if (buecher.get(buchIndex) != null) {
			throw new IllegalStateException("BuchIndex ist bereits belegt");
		}
		buecher.set(buchIndex, buch);
	}


	public Buch herausnehmen (int buchIndex) throws IllegalStateException {
		Buch back = buecher.get(buchIndex);
		if (back == null) {
			throw new IllegalStateException("BuchIndex ist nicht belegt");
		}
		buecher.set(buchIndex, null);
		return back;
	}

	public boolean isVoll () {
		return buecher.stream().noneMatch(Objects::isNull);
	}

	/**
	 * @return
	 */
	@Override
	public Iterator<Buch> iterator () {
		return buecher.iterator();
	}

	/**
	 * @param action The action to be performed for each element
	 */
	@Override
	public void forEach (Consumer<? super Buch> action) {
		buecher.forEach(action);
	}

	/**
	 * @return
	 */
	@Override
	public Spliterator<Buch> spliterator () {
		return buecher.spliterator();
	}

	public int size () {
		return buecher.size();
	}

	public Buch get (int buchIndex) {
		return buecher.get(buchIndex);
	}

	public void entfernen (int buchIndex) {
		buecher.remove(buchIndex);
	}

	//region setter/getter
	public List<Buch> getBuecher () {
		return buecher;
	}

	public UUID getId () {
		return id;
	}

//endregion
}

