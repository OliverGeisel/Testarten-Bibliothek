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

package de.olivergeisel.teddjbrary.user.staff;

import de.olivergeisel.teddjbrary.user.Geschlecht;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class NeuerAngestelltenForm {

	@NotBlank(message = "Vorname darf nicht leer sein")
	private String     vorname;
	@NotBlank(message = "Nachname darf nicht leer sein")
	private String     nachname;
	@Email(message = "Bitte eine gültige E-Mail-Adresse eingeben")
	private String     email;
	@Size(min = 5, message = "Das Passwort muss mindestens 5 Zeichen lang sein")
	private String     passwort;
	@Pattern(regexp = "REINIGUNGSKRAFT|RESTAURATEUR|BIBLIOTHEKAR|ADMIN|MANAGER", message = "Bitte eine gültige Rolle eingeben")
	private String     rolle;
	private Geschlecht geschlecht;
	private int        alter;

	public NeuerAngestelltenForm () {
	}

	public NeuerAngestelltenForm (String vorname, String nachname, String email, String passwort,
			String rolle) {
		this.vorname = vorname;
		this.nachname = nachname;
		this.email = email;
		this.passwort = passwort;
		this.rolle = rolle;
	}

	public String getVorname () {
		return vorname;
	}


	public String getNachname () {
		return nachname;
	}


	public String getEmail () {
		return email;
	}

	public String getPasswort () {
		return passwort;
	}

	public String createUsername () {
		return vorname.toLowerCase() + "_" + nachname.toLowerCase();
	}

	public String getRolle () {
		return rolle;
	}

	public Geschlecht getGeschlecht () {
		return geschlecht;
	}

	public void setGeschlecht (Geschlecht geschlecht) {
		this.geschlecht = geschlecht;
	}

	public int getAlter () {
		return alter;
	}

	public void setAlter (int alter) {
		this.alter = alter;
	}
}
