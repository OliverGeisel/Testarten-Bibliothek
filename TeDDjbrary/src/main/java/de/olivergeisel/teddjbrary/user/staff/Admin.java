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
import jakarta.persistence.Entity;
import org.salespointframework.useraccount.UserAccount;

@Entity
public class Admin extends Angestellter {
	protected Admin() {
		super();
	}

	public Admin(UserAccount account, Geschlecht geschlecht, int alter) {
		super(account, geschlecht, alter);
		if (account == null) {
			throw new IllegalArgumentException("Account must not be null!");
		}
		if (account.getRoles().stream().noneMatch(role -> role.getName().equals("ADMIN"))) {
			throw new IllegalArgumentException("Account must have ADMIN role!");
		}
	}

}
