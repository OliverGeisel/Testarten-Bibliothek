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

package de.olivergeisel.tdd;

import jakarta.persistence.Embeddable;

@Embeddable
public class Adresse {

	private String strasse;
	private String hausnummer;
	private String postleitzahl;
	private String ort;
	private String land;

	public Adresse (String strasse, String hausnummer, String postleitzahl, String ort, String land) {
		this.strasse = strasse;
		this.hausnummer = hausnummer;
		this.postleitzahl = postleitzahl;
		this.ort = ort;
		this.land = land;
	}

	protected Adresse () {

	}

	public String getStrasse () {
		return strasse;
	}

	public void setStrasse (String strasse) {
		this.strasse = strasse;
	}

	public String getHausnummer () {
		return hausnummer;
	}

	public void setHausnummer (String hausnummer) {
		this.hausnummer = hausnummer;
	}

	public String getPostleitzahl () {
		return postleitzahl;
	}

	public void setPostleitzahl (String post) {
		this.postleitzahl = post;
	}

	public String getOrt () {
		return ort;
	}

	public void setOrt (String ort) {
		this.ort = ort;
	}

	public String getLand () {
		return land;
	}

	public void setLand (String land) {
		this.land = land;
	}
}
