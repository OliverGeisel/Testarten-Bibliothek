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

package de.olivergeisel.teddjbrary.rooms;

import de.olivergeisel.teddjbrary.user.visitor.BesucherRepository;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("rooms")
public class RaumController {

	private static final String TEMPLATE_DIR = "rooms/";

	private final RaumReposititory   repo;
	private final BesucherRepository besucherRepo;

	public RaumController(RaumReposititory repo, BesucherRepository besucherRepo) {
		this.repo = repo;
		this.besucherRepo = besucherRepo;
	}

	@GetMapping({"", "/"})
	String overview(Model model) {
		model.addAttribute("raeume", repo.findAll());
		return TEMPLATE_DIR + "overview";
	}

	@GetMapping("/{id}")
	String detail(@PathVariable UUID id, Model model) {
		model.addAttribute("room", repo.findById(id).orElseThrow());
		return TEMPLATE_DIR + "room";
	}

	@PostMapping("{id}/edit")
	@PreAuthorize(value = "hasRole('ADMIN')")
	String edit(@PathVariable UUID id, RaumErstellungsForm form) {
		var raum = repo.findById(id).orElseThrow();
		raum.setName(form.getName());
		raum.setNummer(form.getNummer());
		repo.save(raum);
		return "redirect:/rooms";
	}

	@PostMapping("{id}/betreten")
	String betreten(@PathVariable UUID id, @LoggedIn UserAccount user) {
		var raum = (Leseraum) repo.findById(id).orElseThrow();
		var besucher = besucherRepo.findByUserAccount(user).orElseThrow();
		raum.betreten(besucher);
		repo.save(raum);
		return "redirect:/rooms";
	}

	@PostMapping("/{id}/delete")
	@PreAuthorize(value = "hasRole('ADMIN')")
	String delete(@PathVariable UUID id) {
		repo.deleteById(id);
		return "redirect:/rooms";
	}

	@PostMapping("/new")
	@PreAuthorize(value = "hasRole('ADMIN')")
	String newLeseraum(RaumErstellungsForm form) {
		var raum = new Raum(form.getName(), form.getNummer());
		repo.save(raum);
		return "redirect:/rooms";
	}


}
