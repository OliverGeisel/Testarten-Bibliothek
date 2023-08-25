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

import jakarta.validation.Valid;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

	private final UserManager userManager;

	public UserController(UserManager userManager) {this.userManager = userManager;}


	@GetMapping("/login")
	public String login() {
		return "login";
	}


	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("types", UserManager.ALLE_BESUCHER_ROLES.stream().map(Role::getName).toList());
		return "registration";
	}

	@PostMapping("/register")
	public String register(@Valid UserRegistrationForm form, Model model, Errors result) {
		if (result.hasErrors()) {
			return "registration";
		}
		var user = userManager.registerUser(form);
		return "redirect:/login";
	}

	@GetMapping("/profile")
	public String profile(Model model, @LoggedIn UserAccount userAccount) {
		model.addAttribute("user", userManager.findByAccount(userAccount));
		return "profile";
	}

	@PostMapping("/profile/personalDataUpdate")
	public String updatePersonalData(@Valid PersonalDataUpdateForm form, Errors result,
			@LoggedIn UserAccount userAccount) {
		if (result.hasErrors()) {
			return "profile";
		}
		userManager.updatePersonalData(form, userAccount);
		return "redirect:/profile";
	}
}
