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

package de.olivergeisel.teddjbrary.user;

import jakarta.validation.constraints.*;


public class UserRegistrationForm {

	@NotBlank(message = "Username darf nicht leer sein")
	private String username;
	@NotBlank(message = "Vorname darf nicht leer sein")
	private String vorname;
	@NotBlank(message = "Rolle darf nicht leer sein")
	private String rolle;
	@NotBlank(message = "Passwort darf nicht leer sein")
	@Pattern(regexp = "^(?=.*[A-Z]).*$", message = "Passwort muss mindestens einen Großbuchstaben enthalten")
	@Pattern(regexp = "^(?=.*[a-z]).*$", message = "Passwort muss mindestens einen Kleinbuchstaben enthalten")
	@Pattern(regexp = "^(?=.*\\d).*$", message = "Passwort muss mindestens eine Zahl enthalten")
	@Pattern(regexp = "^(?=.*[!@#$%^&*]).*$", message = "Passwort muss mindestens ein Sonderzeichen enthalten")
	@Size(min = 5, message = "Passwort muss mindestens 5 Zeichen lang sein")
	private String passwort;
	@NotBlank(message = "Nachname darf nicht leer sein")
	private String nachname;
	@NotBlank(message = "Passwort wiederholen darf nicht leer sein")
	private String passwortWiederholung;
	@Email(message = "Email muss gültig sein")
	private String email;
	@Min(value = 13, message = "Du musst mindestens 13 Jahre alt sein")
	private int    alter;

	//region setter/getter
	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getRolle () {
		return rolle;
	}

	public void setRolle (String rolle) {
		this.rolle = rolle;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public String getPasswortWiederholung() {
		return passwortWiederholung;
	}

	public void setPasswortWiederholung(String passwortWiederholung) {
		this.passwortWiederholung = passwortWiederholung;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAlter () {
		return alter;
	}

	public void setAlter (int alter) {
		this.alter = alter;
	}
//endregion


}
