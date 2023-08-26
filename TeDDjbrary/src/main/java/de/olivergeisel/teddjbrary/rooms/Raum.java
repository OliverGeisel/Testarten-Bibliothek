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

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
public class Raum {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private UUID   id;
	private String name;
	private int    nummer;
	private String beschreibung;

	public Raum() {
	}

	public Raum(String name, int nummer) {
		this.name = name;
		this.nummer = nummer;
	}

	//region setter/getter
	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNummer() {
		return nummer;
	}

	public void setNummer(int number) {
		this.nummer = number;
	}
//endregion

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Raum raum = (Raum) o;

		return Objects.equals(id, raum.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
