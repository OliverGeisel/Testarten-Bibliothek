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

import jakarta.persistence.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;


/**
 * Repräsentiert ein Buch in einer Bibliothek.
 * Wenn es mehrere Instanzen des gleichen physischen Buches gibt, so werden diese durch einen Code unterschieden.
 */
@Entity
public class Buch implements Comparable<Buch>, Verschmutzbar {
	private final String  titel;
	@Embedded
	private final ISBN    isbn;
	@Column(nullable = false)
	private final long    code;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private       UUID    id;
	private       String  autor;
	private       boolean ausgeliehen;
	private       double  beschaedigung;
	private       URI     bild;

	private LocalDate ausleihdatum;

	private double verschmutzung;

	public Buch() {
		this("", new ISBN());
	}

	public Buch(String titel, ISBN isbn) {
		this(titel, "", isbn, URI.create("http://localhost:8080/images/placeholder.png"));
	}

	public Buch(String titel, String autor, ISBN isbn, URI bild) {
		this.titel = titel;
		code = new Random().nextInt();
		this.autor = autor;
		this.isbn = isbn;
		this.bild = bild;
	}

	/**
	 * Setzt den Status auf ausgeliehen. Kann nicht ausgeliehen werden, wenn es nicht verfügbar ist.
	 */
	public void ausleihen() {
		ausgeliehen = true;
		ausleihdatum = LocalDate.now();
	}

	/**
	 * Erlaubt das Ausleihen des Buches.
	 */
	public void verfuegbarMachen() {
		ausgeliehen = false;
		ausleihdatum = null;
	}

	/**
	 * Fügt eine kleine Beschädigung hinzu.
	 */
	public void beschaedigen() {
		beschaedigung += Math.random() * 0.5;
	}

	/**
	 * Das Buch wird stark beschädigt und ist zur Reparatur zu bringen.
	 */
	public void starkBeschaedigen() {
		beschaedigung = Math.max(0.8, beschaedigung + 0.1);
	}

	/**
	 * Repariert das Buch wieder und macht es wieder nutzbar.
	 */
	public void reparieren() {
		beschaedigung = 0.01;
	}

	/**
	 * Gibt Auskunft, ob ein Buch vom Inhalt her das Gleiche ist
	 *
	 * @param buch das zu vergleichende Buch
	 * @return true, wenn die ISBN gleich ist.
	 */
	public boolean isGleichesBuch(Buch buch) {
		return isbn.equals(buch.isbn);
	}

	/**
	 * Gibt Auskunft, ob ein Buch vom Inhalt und Code her das Gleiche ist.
	 * <br>
	 * Wenn equals() true zurückgibt, dann muss auch isIdentischesBuch() true zurückgeben.
	 *
	 * @param buch das zu vergleichende Buch
	 * @return true, wenn die ISBN und der Code gleich ist.
	 */
	public boolean isIdentischesBuch(Buch buch) {
		return this.isbn.equals(isbn) && this.code == buch.code;
	}

	/**
	 * Reinigt das Buch vollständig.
	 */
	@Override
	public void saeubern() {
		verschmutzung = 0.0;
	}

	/**
	 * Verschmutzt das Buch etwas.
	 */
	@Override
	public void verschmutzen() {
		verschmutzung += 0.07;
	}

	/**
	 * Compares this object with the specified object for order.  Returns a
	 * negative integer, zero, or a positive integer as this object is less
	 * than, equal to, or greater than the specified object.
	 *
	 * <p>The implementor must ensure
	 * {@code sgn(x.compareTo(y)) == -sgn(y.compareTo(x))}
	 * for all {@code x} and {@code y}.  (This
	 * implies that {@code x.compareTo(y)} must throw an exception iff
	 * {@code y.compareTo(x)} throws an exception.)
	 *
	 * <p>The implementor must also ensure that the relation is transitive:
	 * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
	 * {@code x.compareTo(z) > 0}.
	 *
	 * <p>Finally, the implementor must ensure that {@code x.compareTo(y)==0}
	 * implies that {@code sgn(x.compareTo(z)) == sgn(y.compareTo(z))}, for
	 * all {@code z}.
	 *
	 * <p>It is strongly recommended, but <i>not</i> strictly required that
	 * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
	 * class that implements the {@code Comparable} interface and violates
	 * this condition should clearly indicate this fact.  The recommended
	 * language is "Note: this class has a natural ordering that is
	 * inconsistent with equals."
	 *
	 * <p>In the foregoing description, the notation
	 * {@code sgn(}<i>expression</i>{@code )} designates the mathematical
	 * <i>signum</i> function, which is defined to return one of {@code -1},
	 * {@code 0}, or {@code 1} according to whether the value of
	 * <i>expression</i> is negative, zero, or positive, respectively.
	 *
	 * @param o the object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object
	 * is less than, equal to, or greater than the specified object.
	 *
	 * @throws NullPointerException if the specified object is null
	 * @throws ClassCastException   if the specified object's type prevents it
	 *                              from being compared to this object.
	 */

	@Override
	public int compareTo(Buch o) {
		return this.isbn.compareTo(o.isbn);
	}

	//region setter/getter
	public UUID getId() {
		return id;
	}

	/**
	 * Gibt Auskunft, ob ein Buch gereinigt werden sollte.
	 *
	 * @return true, wenn die Verschmutzung größer als 0.5 ist.
	 */
	@Override
	public boolean isDreckig() {
		return verschmutzung > 0.5;
	}

	public String getTitel() {
		return titel;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public ISBN getIsbn() {
		return isbn;
	}

	public long getCode() {
		return code;
	}

	public double getBeschaedigung() {
		return beschaedigung;
	}

	public LocalDate getAusleihdatum() {
		return ausleihdatum;
	}

	public boolean isAusgeliehen() {
		return ausgeliehen;
	}

	public boolean isVerfuegbar() {
		return !isAusgeliehen();
	}

	public URI getBild() {
		return bild;
	}

	public void setBild(URI bild) {
		this.bild = bild;
	}
//endregion

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Buch buch = (Buch) o;
		return id.equals(buch.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(isbn, code);
	}
}
