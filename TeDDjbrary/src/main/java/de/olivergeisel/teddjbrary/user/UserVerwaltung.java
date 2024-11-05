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

import de.olivergeisel.teddjbrary.user.staff.AngestelltenVerwaltung;
import de.olivergeisel.teddjbrary.user.staff.Angestellter;
import de.olivergeisel.teddjbrary.user.visitor.*;
import org.salespointframework.useraccount.Password;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserVerwaltung {

	static final Role       ROLE_USER           = Role.of("USER");
	static final Role       ROLE_DOZENT         = Role.of("DOZENT");
	static final Role       ROLE_STUDIERENDER   = Role.of("STUDIERENDER");
	static final List<Role> ALLE_BESUCHER_ROLES = List.of(ROLE_DOZENT, ROLE_STUDIERENDER);

	static final Role       ROLE_ANGESTELLTER      = Role.of("ANGESTELLTER");
	static final Role       ROLE_BIBLIOTHEKAR      = Role.of("BIBLIOTHEKAR");
	static final Role       ROLE_RESTAURATOR       = Role.of("RESTAURATOR");
	static final Role       ROLE_REINIGUNGSKRAFT   = Role.of("REINIGUNGSKRAFT");
	static final List<Role> ALLE_ROLES             = List.of(ROLE_USER, ROLE_DOZENT, ROLE_STUDIERENDER,
			ROLE_ANGESTELLTER, ROLE_BIBLIOTHEKAR, ROLE_RESTAURATOR, ROLE_REINIGUNGSKRAFT);
	static final Role       ROLE_ADMIN             = Role.of("ADMIN");
	static final List<Role> ALLE_ANGESTELLTE_ROLES =
			List.of(ROLE_ANGESTELLTER, ROLE_BIBLIOTHEKAR, ROLE_RESTAURATOR, ROLE_REINIGUNGSKRAFT, ROLE_ADMIN);


	private final UserAccountManagement  userAccountManagement;
	private final BesucherRepository     besucherRepository;
	private final AngestelltenVerwaltung angestellte;
	private final Kundenregister register;
	public UserVerwaltung (UserAccountManagement userAccountManagement, BesucherRepository besucherRepository,
			AngestelltenVerwaltung angestellte, Kundenregister register) {
		this.userAccountManagement = userAccountManagement;
		this.besucherRepository = besucherRepository;
		this.angestellte = angestellte;
		this.register = register;
	}

	public Besucher registerUser (UserRegistrationForm form) throws IllegalArgumentException {
		if (userAccountManagement.findByUsername(form.getUsername()).isPresent()) {
			throw new IllegalArgumentException("Username already taken");
		}
		var account = userAccountManagement.create(form.getUsername(),
				Password.UnencryptedPassword.of(form.getPasswort()), ROLE_USER);
		account.setFirstname(form.getVorname());
		account.setLastname(form.getNachname());
		account.add(Role.of(form.getRolle().toUpperCase()));
		var nutzer = switch (form.getRolle()) {
			case "DOZENT" -> new Dozent(account);
			case "STUDIERENDER" -> new Studierender(account);
			default -> throw new IllegalStateException("Unexpected value: " + form.getRolle());
		};
		userAccountManagement.save(account);
		return besucherRepository.save(nutzer);
	}

	public Benutzer findByAccount (UserAccount userAccount) throws NoSuchElementException {
		if (userAccount.getRoles().stream().anyMatch(ALLE_BESUCHER_ROLES::contains)) {
			return besucherRepository.findByUserAccount(userAccount).orElseThrow();
		} else if (userAccount.getRoles().stream().anyMatch(ALLE_ANGESTELLTE_ROLES::contains)) {
			return angestellte.findByUserAccount(userAccount).orElseThrow();
		} else {
			throw new NoSuchElementException("UserAccount not found");
		}
	}


	public <T extends Angestellter> T saveAngestellten (T angestellter) {
		return angestellte.save(angestellter);
	}

	public <T extends Besucher> T saveBesucher (T besucher) {
		var temp = besucherRepository.save(besucher);
		register.addBesucher(besucher);
		return temp;
	}

	public void updatePersonalData (PersonalDataUpdateForm form, UserAccount userAccount) {
		userAccount.setFirstname(form.getVorname());
		userAccount.setLastname(form.getNachname());
		userAccount.setEmail(form.getEmail());
		userAccountManagement.save(userAccount);
	}

	public void deleteAccount (UserAccount userAccount) {
		userAccountManagement.delete(userAccount);
	}

	public void loeschen (Benutzer nutzer) {
		if (nutzer instanceof Angestellter angestellter) {
			angestellte.entlassen(angestellter);
		} else if (nutzer instanceof Besucher besucher) {
			besucherRepository.delete(besucher);
		}
	}
}
