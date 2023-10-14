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
import de.olivergeisel.teddjbrary.user.visitor.Besucher;
import de.olivergeisel.teddjbrary.user.visitor.BesucherRepository;
import de.olivergeisel.teddjbrary.user.visitor.Dozent;
import de.olivergeisel.teddjbrary.user.visitor.Studierender;
import org.salespointframework.useraccount.Password;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserVerwaltung {

	private static final Role       ROLE_USER           = Role.of("USER");
	private static final Role       ROLE_DOZENT         = Role.of("DOZENT");
	private static final Role       ROLE_STUDIERENDER   = Role.of("STUDIERENDER");
	static final         List<Role> ALLE_BESUCHER_ROLES = List.of(ROLE_DOZENT, ROLE_STUDIERENDER);

	private static final Role       ROLE_ANGESTELLTER      = Role.of("ANGESTELLTER");
	private static final Role       ROLE_BIBLIOTHEKAR      = Role.of("BIBLIOTHEKAR");
	private static final Role       ROLE_RESTAURATOR       = Role.of("RESTAURATOR");
	private static final Role       ROLE_REINIGUNGSKRAFT   = Role.of("REINIGUNGSKRAFT");
	static final List<Role> ALLE_ROLES = List.of(ROLE_USER, ROLE_DOZENT, ROLE_STUDIERENDER,
			ROLE_ANGESTELLTER, ROLE_BIBLIOTHEKAR, ROLE_RESTAURATOR, ROLE_REINIGUNGSKRAFT);
	private static final Role       ROLE_ADMIN             = Role.of("ADMIN");
	static final         List<Role> ALLE_ANGESTELLTE_ROLES =
			List.of(ROLE_ANGESTELLTER, ROLE_BIBLIOTHEKAR, ROLE_RESTAURATOR, ROLE_REINIGUNGSKRAFT, ROLE_ADMIN);


	private final UserAccountManagement  userAccountManagement;
	private final BesucherRepository     besucherRepository;
	private final AngestelltenVerwaltung angestellte;

	public UserVerwaltung(UserAccountManagement userAccountManagement, BesucherRepository besucherRepository,
			AngestelltenVerwaltung angestellte) {
		this.userAccountManagement = userAccountManagement;
		this.besucherRepository = besucherRepository;
		this.angestellte = angestellte;
	}

	public Besucher registerUser(UserRegistrationForm form) {
		var account = userAccountManagement.create(form.getUsername(),
				Password.UnencryptedPassword.of(form.getPasswort()), ROLE_USER);
		account.setFirstname(form.getVorname());
		account.setLastname(form.getNachname());
		account.add(Role.of(form.getRole().toUpperCase()));
		var nutzer = switch (form.getRole()) {
			case "DOZENT" -> new Dozent(account);
			case "STUDIERENDER" -> new Studierender(account);
			default -> throw new IllegalStateException("Unexpected value: " + form.getRole());
		};
		userAccountManagement.save(account);
		return besucherRepository.save(nutzer);
	}

	public Benutzer findByAccount(UserAccount userAccount) throws NoSuchElementException {
		if (userAccount.getRoles().stream().anyMatch(ALLE_BESUCHER_ROLES::contains)) {
			return besucherRepository.findByUserAccount(userAccount).orElseThrow();
		} else if (userAccount.getRoles().stream().anyMatch(ALLE_ANGESTELLTE_ROLES::contains)) {
			return angestellte.findByUserAccount(userAccount).orElseThrow();
		} else {
			throw new NoSuchElementException("UserAccount not found");
		}
	}



	public <T extends Angestellter> T saveAngestellten(T angestellter) {
		return angestellte.save(angestellter);
	}

	public <T extends Besucher> T saveBesucher(T besucher) {
		return besucherRepository.save(besucher);
	}

	public void updatePersonalData(PersonalDataUpdateForm form, UserAccount userAccount) {
		userAccount.setFirstname(form.getVorname());
		userAccount.setLastname(form.getNachname());
		userAccount.setEmail(form.getEmail());
		userAccountManagement.save(userAccount);
	}
}
