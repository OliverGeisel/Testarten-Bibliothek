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

public class EditForm {

	private String vorname, nachname, telefonnummer;

	public EditForm (String vorname, String nachname, String telefonnummer) {
		this.vorname = vorname;
		this.nachname = nachname;
		this.telefonnummer = telefonnummer;
	}

	public String getVorname () {
		return vorname;
	}

	public void setVorname (String vorname) {
		this.vorname = vorname;
	}

	public String getNachname () {
		return nachname;
	}

	public void setNachname (String nachname) {
		this.nachname = nachname;
	}

	public String getTelefonnummer () {
		return telefonnummer;
	}

	public void setTelefonnummer (String telefonnummer) {
		this.telefonnummer = telefonnummer;
	}

}
