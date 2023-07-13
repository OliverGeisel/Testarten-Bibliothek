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

import org.salespointframework.core.DataInitializer;
import org.salespointframework.useraccount.Password;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManagement;
import org.springframework.stereotype.Component;

@Component
public class UserInitializer implements DataInitializer {

	private final UserAccountManagement userAccountManagement;

	public UserInitializer(UserAccountManagement userAccountManagement) {
		this.userAccountManagement = userAccountManagement;
	}

	@Override
	public void initialize() {
		var account = userAccountManagement.create("admin", Password.UnencryptedPassword.of("root"), Role.of("ADMIN"));
		userAccountManagement.save(account);
		account = userAccountManagement.create("geheim", Password.UnencryptedPassword.of("Wasserfallschutzsystemüberwachungsbeamter"), Role.of("BESONDERS"));
		userAccountManagement.save(account);
	}
}
