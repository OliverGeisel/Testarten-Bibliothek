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

package de.olivergeisel.teddjbrary.inventory;

import de.olivergeisel.teddjbrary.core.Buch;
import de.olivergeisel.teddjbrary.user.visitor.BesucherRepository;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("inventory")
public class BestandsController {
	private final BestandsVerwaltung verwaltung;
	private final BesucherRepository besucherRepository;


	public BestandsController (BestandsVerwaltung verwaltung, BesucherRepository besucherRepository) {
		this.verwaltung = verwaltung;
		this.besucherRepository = besucherRepository;
	}

	@GetMapping({"", "/"})
	public String overview (@RequestParam(required = false) Optional<Integer> pageNum, Model model) {
		var correctPageNum = Math.max(0, pageNum.orElse(0));
		var page = PageRequest.of(correctPageNum, 20);
		model.addAttribute("buecher", verwaltung.findAll(page));
		model.addAttribute("pageNum", correctPageNum);
		model.addAttribute("hasNextPage", verwaltung.findAll(page.next()).hasContent());
		model.addAttribute("totalPages", (int) Math.ceil(verwaltung.zaehlen() / 20.));
		return "inventory";
	}

	@GetMapping("{id}")
	public String detail (@PathVariable("id") UUID id, Model model) {
		try {
			var buch = verwaltung.findById(id).orElseThrow();
			var regale = verwaltung.getRegalCode(buch);
			model.addAttribute("buch", buch);
			model.addAttribute("regale", regale);
		} catch (NoSuchElementException e) {
			return "redirect:/inventory";
		}
		return "inventory-detail";
	}

	@PostMapping("ausleihen/{id}")
	@PreAuthorize("isAuthenticated()")
	public String ausleihen (@PathVariable("id") Buch buch, @LoggedIn UserAccount account) {
		besucherRepository.findByUserAccount(account).ifPresent(besucher -> verwaltung.ausleihen(buch, besucher));
		return "redirect:/inventory";
	}
}
