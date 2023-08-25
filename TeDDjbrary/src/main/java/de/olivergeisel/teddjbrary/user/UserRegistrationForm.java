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

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class UserRegistrationForm {

	@NotBlank(message = "Username darf nicht leer sein")
	private String username;
	@NotBlank(message = "Vorname darf nicht leer sein")
	private String vorname;
	@NotBlank(message = "Rolle darf nicht leer sein")
	private String role;
	@NotBlank(message = "Nachname darf nicht leer sein")
	private String nachname;
	@NotBlank(message = "Passwort darf nicht leer sein")
	private String passwort;
	@NotBlank(message = "Passwort wiederholen darf nicht leer sein")
	private String passwortWiederholung;
	@Email(message = "Email muss gültig sein")
	private String email;
	@Min(value = 13, message = "Du musst mindestens 13 Jahre alt sein")
	private int    age;

	//region setter/getter
	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
//endregion


}
