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

import org.springframework.format.Formatter;

public class AdresseFormatter implements Formatter<Adresse> {
	@Override
	public Adresse parse (String text, java.util.Locale locale) {
		String[] parts = text.split(",");
		return new Adresse(parts[0], parts[1], parts[2], parts[3], parts[4]);
	}

	@Override
	public String print (Adresse adresse, java.util.Locale locale) {
		return """ 
				Straße: %s <br>
				Hausnummer: %s <br>
				Postleitzahl: %s <br>
				Ort: %s <br>
				Land: %s""".formatted(adresse.getStrasse(), adresse.getHausnummer(), adresse.getPostleitzahl(),
				adresse.getOrt(), adresse.getLand());
	}
}
