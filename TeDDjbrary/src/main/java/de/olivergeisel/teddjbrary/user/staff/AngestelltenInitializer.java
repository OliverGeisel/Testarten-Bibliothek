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
import jakarta.transaction.Transactional;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.useraccount.Password;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(10)
@Transactional
public class AngestelltenInitializer implements DataInitializer {

	private final AngestellterRepository angestellterRepository;
	private final UserAccountManagement  userAccountManagement;

	public AngestelltenInitializer(AngestellterRepository angestellterRepository,
			UserAccountManagement userAccountManagement) {
		this.angestellterRepository = angestellterRepository;
		this.userAccountManagement = userAccountManagement;
	}

	/**
	 * Called on application startup to trigger data initialization. Will run inside a transaction.
	 */
	@Override
	public void initialize() {
		var managerAccount = userAccountManagement.create("manager", Password.UnencryptedPassword.of(
				"IchLiebeGeldUndGeldLiebtMich"), AngestelltenVerwaltung.MANAGER);
		managerAccount.setFirstname("Antonia");
		managerAccount.setLastname("Geldmacher");
		managerAccount.setEmail("koenigin@tebib.de");
		var manager = new Verwaltung(managerAccount, Geschlecht.WEIBLICH, 45);
		angestellterRepository.save(manager);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		var account = userAccountManagement.create("admin", Password.UnencryptedPassword.of("root"), Role.of("ADMIN"));
		account.setFirstname("Admin");
		account.setLastname("BigBoss");
		account.setEmail("superadmin@tebib.de");
		var admin = new Bibliothekar(account, Geschlecht.MAENNLICH, 25);
		userAccountManagement.save(account);
		angestellterRepository.save(admin);

		account = userAccountManagement.create("geheim",
				Password.UnencryptedPassword.of("Wasserfallschutzsystemüberwachungsbeamter"), Role.of("BESONDERS"));
		userAccountManagement.save(account);

		var adminAccount = userAccountManagement.create("admin2", Password.UnencryptedPassword.of("IsamGuämP5mj"),
				AngestelltenVerwaltung.ADMIN);
		adminAccount.setFirstname("Oliver");
		adminAccount.setLastname("Geisel");
		adminAccount.setEmail("admin2@tebib.de");
		var admin2 = new Verwaltung(adminAccount, Geschlecht.MAENNLICH, 26);
		angestellterRepository.save(admin2);
	}
}