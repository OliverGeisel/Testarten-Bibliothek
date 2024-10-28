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

package de.olivergeisel.teddjbrary.core;


import jakarta.persistence.Embeddable;

import java.util.Arrays;

/**
 * Eine ISBN-13. Die ISBN ist eine eindeutige Representation eines Buches.
 * <p>
 * Eine ISBN besteht aus 5 Teilen. Dem Präfix, der Gruppe, der Verlagsnummer, des Titels und der Prüfziffer.
 * <p>
 * Die Prüfziffer wird nicht überprüft
 *
 * @param praefix     Präfix der ISBN. Entweder 978 oder 979
 * @param gruppe      Eine einstellige Zahl, die die Gruppe repräsentiert
 * @param verlagnr    Nummer die den Verlag repräsentiert. Muss kleiner 100_000 sein.
 * @param titelnr     Nummer des Buchtitels im Verlag. Muss kleiner 1_000 sein
 * @param pruefziffer Eine einstellige Zahl, die die anderen Zahlen auf Korrektheit prüft.
 */
// records sind spezielle Klassen. Sie haben nur (default-)getter. equals, toString und hashCode sind bereits überschrieben
@Embeddable
public record ISBN(int praefix, int gruppe, int verlagnr, int titelnr, int pruefziffer) implements Comparable<ISBN> {

	public static final ISBN NullISBN = new ISBN(978, 0, 0, 0, 0);

	/**
	 * @param praefix     Präfix der ISBN. Entweder 978 oder 979
	 * @param gruppe      Eine einstellige Zahl, die die Gruppe repräsentiert
	 * @param verlagnr    Nummer die den Verlag repräsentiert. Muss kleiner 100_000 sein.
	 * @param titelnr     Nummer des Buchtitels im Verlag. Muss kleiner 1_000 sein
	 * @param pruefziffer Eine einstellige Zahl, die die anderen Zahlen auf Korrektheit prüft.
	 */
	// Kontrolle der Parameter
	public ISBN {
		if (praefix < 978 || praefix > 979) {
			throw new IllegalArgumentException("Präfix ist unzulässig");
		}
		if (gruppe > 9 || gruppe < 0) {
			throw new IllegalArgumentException("Gruppe ist ungültig");
		}
		if (verlagnr >= 100_000 || verlagnr < 0) {
			throw new IllegalArgumentException("Verlagsnummer zu groß");
		}
		if (titelnr >= 1_000 || titelnr < 0) {
			throw new IllegalArgumentException("Titel zu groß");
		}
		if (pruefziffer > 9 || pruefziffer < 0) {
			throw new IllegalArgumentException("Prüfziffer ungültig");
		}
	}

	public ISBN() {
		this(978, 0, 0, 0, 0);
	}

	/**
	 * Nimmt eine ISBN-Stringrepräsentation und gibt das passende ISBN-Objekt zurück.
	 *
	 * @param isbn String der eine ISBN-13 ist.
	 * @return ISBN die zu dem angegeben String passt
	 *
	 * @throws IllegalArgumentException, wenn der String keine gültige ISBN-13 ist.
	 */
	public static ISBN fromString(String isbn) throws IllegalArgumentException {
		var numbers = isbn.split("-");
		var intNumbers = Arrays.stream(numbers).mapToInt(Integer::parseInt).toArray();
		if (intNumbers.length < 5) {
			throw new IllegalArgumentException("ISBN nicht korrekt. Es wird eine ISBN-13 mit Trennstrichen benötigt!");
		}
		return new ISBN(intNumbers[0], intNumbers[1], intNumbers[2], intNumbers[3], intNumbers[4]);
	}

	/**
	 * Nimmt eine ISBN-Stringrepräsentation und gibt das passende ISBN-Objekt zurück.
	 * Sollte die Prüfziffer keine Zahl sein, wird sie ignoriert und auf 0 gesetzt.
	 *
	 * @param isbn String der eine ISBN-10 ist.
	 * @return ISBN die zu dem angegeben String passt
	 *
	 * @throws IllegalArgumentException, wenn der String keine gültige ISBN-10 ist.
	 */
	public static ISBN fromStringOhneTrennung(String isbn) {
		if (isbn == null) {
			return NullISBN;
		}
		if (isbn.length() != 10) {
			throw new IllegalArgumentException("ISBN nicht korrekt. Es wird eine ISBN-10 ohne Trennstriche benötigt!");
		}
		var pre = 978;
		var grp = Integer.parseInt(isbn.substring(0, 1));
		var verlag = Integer.parseInt(isbn.substring(1, 6));
		var titel = Integer.parseInt(isbn.substring(6, 9));
		int pruef = 0;
		try { // ignoriere pruefziffer wenn keine Nummer
			pruef = Integer.parseInt(isbn.substring(9, 10));
		} catch (Exception ignored) {
		}
		return new ISBN(pre, grp, verlag, titel, pruef);
	}

	/**
	 * Gibt die ISBN mit Trennstrichen zurück.
	 *
	 * @return Die ISBN mit führenden Nullen und Trennstrichen.
	 */
	public String mitTrennstrich() {
		return String.format("%d-%d-%05d-%03d-%d", praefix, gruppe, verlagnr, titelnr, pruefziffer);
	}

	/**
	 * Gibt die ISBN mit Leerzeichen zurück.
	 *
	 * @return Die ISBN mit führenden Nullen und Leerzeichen.
	 */
	public String ohneTrennstrich() {
		return String.format("%d %d %05d %03d %d", praefix, gruppe, verlagnr, titelnr, pruefziffer);
	}

	@Override
	public int compareTo(ISBN isbn) {
		return mitTrennstrich().compareTo(isbn.mitTrennstrich());
	}
}
