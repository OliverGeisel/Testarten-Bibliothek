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

import org.salespointframework.useraccount.Password;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class AngestelltenVerwaltung {

	static final Role REINIGUNG    = Role.of("REINIGUNGSKRAFT");
	static final Role RESTAURATEUR = Role.of("RESTAURATEUR");
	static final Role BIBLIOTHEKAR = Role.of("BIBLIOTHEKAR");
	static final Role ADMIN        = Role.of("ADMIN");
	static final Role MANAGER      = Role.of("MANAGER");
	private final AngestellterRepository angestellterRepository;
	private final UserAccountManagement userAccountManager;
	private Logger logger          = LoggerFactory.getLogger(AngestelltenVerwaltung.class);

	public AngestelltenVerwaltung (AngestellterRepository angestellterRepository,
			UserAccountManagement userAccountManager) {
		this.angestellterRepository = angestellterRepository;
		this.userAccountManager = userAccountManager;
	}

	public boolean isAngestellt (Angestellter angestellter) {
		if (angestellter == null || angestellter.getId() == null) {
			return false;
		}
		return angestellterRepository.findById(angestellter.getId()).isPresent();
	}

	public boolean isAngestellt (UserAccount userAccount) {
		if (userAccount == null) {
			return false;
		}
		return angestellterRepository.findByUserAccount(userAccount).isPresent();
	}

	public boolean entlassen (Angestellter angestellter) {
		if (angestellter == null || angestellter.getId() == null) {
			return false;
		}
		angestellterRepository.delete(angestellter);
		logger.info("Angestellter {} wurde entlassen", angestellter.getId());
		return true;
	}

	public boolean entlassen (UUID id) {
		if (id == null) {
			return false;
		}
		angestellterRepository.findById(id).ifPresent(angestellterRepository::delete);
		return true;
	}

	public boolean deaktivieren (Angestellter angestellter) {
		if (angestellter == null || angestellter.getId() == null) {
			return false;
		}
		if (!angestellter.getUserAccount().isEnabled()) {
			throw new IllegalStateException("Benutzerkonto ist bereits deaktiviert");
		}
		angestellter.getUserAccount().setEnabled(false);
		angestellterRepository.save(angestellter);
		return true;
	}

	public boolean deaktivieren (UUID id) {
		if (id == null) {
			return false;
		}
		return deaktivieren(angestellterRepository.findById(id).orElseThrow());
	}

	public boolean aktivieren (Angestellter angestellter) {
		if (angestellter == null || angestellter.getId() == null) {
			return false;
		}
		if (angestellter.getUserAccount().isEnabled()) {
			throw new IllegalStateException("Benutzerkonto ist bereits aktiviert");
		}
		angestellter.getUserAccount().setEnabled(true);
		angestellterRepository.save(angestellter);
		return true;
	}

	public boolean aktivieren (UUID id) {
		if (id == null) {
			return false;
		}
		return aktivieren(angestellterRepository.findById(id).orElseThrow());
	}

	public boolean rolleAendern (Angestellter angestellter, String rolle) {
		if (angestellter == null || angestellter.getId() == null) {
			return false;
		}
		if (angestellter.getUserAccount().hasRole(getRolle(rolle))) {
			throw new IllegalStateException("Benutzerkonto hat bereits die Rolle: " + rolle);
		}
		var account = angestellter.getUserAccount();
		account.remove(REINIGUNG);
		account.remove(RESTAURATEUR);
		account.remove(BIBLIOTHEKAR);
		account.remove(ADMIN);
		account.remove(MANAGER);
		account.add(getRolle(rolle));
		userAccountManager.save(account);
		return true;
	}

	public void anstellen (NeuerAngestelltenForm form) {
		var rolle = getRolle(form.getRolle());
		var account = userAccountManager.create(form.createUsername(),
				Password.UnencryptedPassword.of(form.getPasswort()),
				form.getEmail(), rolle);
		var userName = account.getUsername();
		if (angestellterRepository.existsByUserAccount_Username(userName)) {
			logger.warn("Benutzername {} existiert bereits", userName);
			userName = findeAnderenBenutzernamen(userName);
			logger.info("Benutzername {} wird verwendet", userName);
		}
		account.setUsername(userName);
		account.setFirstname(form.getVorname());
		account.setLastname(form.getNachname());
		userAccountManager.save(account);
		var angestellter = switch (form.getRolle()) {
			case "REINIGUNGSKRAFT" -> new Reinigungskraft(account, form.getGeschlecht(), form.getAlter());
			case "RESTAURATEUR" -> new Restaurateur(account, form.getGeschlecht(), form.getAlter());
			case "BIBLIOTHEKAR" -> new Bibliothekar(account, form.getGeschlecht(), form.getAlter());
			case "MANAGER" -> new Verwaltung(account, form.getGeschlecht(), form.getAlter());
			case "ADMIN" -> new Admin(account, form.getGeschlecht(), form.getAlter());
			default -> throw new IllegalStateException("Unexpected value: " + form.getRolle());
		};
		angestellterRepository.save(angestellter);
	}

	private String findeAnderenBenutzernamen (String userName) {
		String neuerName;
		int i = 1;
		do {
			neuerName = userName + "_" + i;
		}
		while (angestellterRepository.existsByUserAccount_Username(neuerName));
		return neuerName;
	}

	private Role getRolle (String role) {
		return switch (role) {
			case "REINIGUNGSKRAFT" -> REINIGUNG;
			case "RESTAURATEUR" -> RESTAURATEUR;
			case "BIBLIOTHEKAR" -> BIBLIOTHEKAR;
			case "ADMIN" -> ADMIN;
			case "MANAGER" -> MANAGER;
			default -> throw new IllegalArgumentException("Ungültige Rolle");
		};
	}

	public Collection<Angestellter> getAngestellte (Bereich bereich) {
		return Collections.unmodifiableSet(angestellterRepository.findByBereich(bereich).toSet());
	}

	public <T extends Angestellter> T save (T angestellter) {
		return angestellterRepository.save(angestellter);
	}

	public Iterable<Angestellter> findAll () {
		return angestellterRepository.findAll();
	}

	public Optional<Angestellter> findByUserAccount (UserAccount userAccount) {
		return angestellterRepository.findByUserAccount(userAccount);
	}
}

