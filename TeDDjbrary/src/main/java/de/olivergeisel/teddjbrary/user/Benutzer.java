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

import jakarta.persistence.*;
import org.salespointframework.useraccount.UserAccount;

import java.util.UUID;

@Entity
public abstract class Benutzer implements Person {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", nullable = false)
	private UUID id;
	@OneToOne(optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
	private UserAccount userAccount;

	protected Benutzer() {}

	protected Benutzer(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

//region setter/getter
	public UUID getId() {return id;}

	public UserAccount getUserAccount() {return userAccount;}
//endregion


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Benutzer besucher)) return false;

		return id.equals(besucher.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
