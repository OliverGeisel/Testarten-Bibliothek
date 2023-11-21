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

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
@RequestMapping("angestellte")
public class AngestelltenController {

	private static final String PATH = "staff/";

	private final AngestelltenVerwaltung verwaltung;

	public AngestelltenController(AngestelltenVerwaltung verwaltung) {this.verwaltung = verwaltung;}

	@GetMapping({"", "/"})
	public String angestellte(Model model) {
		model.addAttribute("angestellte", verwaltung.findAll());
		return PATH + "overview";
	}

	@PostMapping("anstellen")
	public String anstellen (NeuerAngestelltenForm form) {
		verwaltung.anstellen(form);
		return "redirect:";
	}


	@PostMapping("entlassen")
	public String entlassen (UUID id) {
		verwaltung.entlassen(id);
		return "redirect:";
	}

	@PostMapping("deaktivieren")
	public String deaktivieren (UUID id) {
		verwaltung.deaktivieren(id);
		return "redirect:";
	}

	@PostMapping("aktivieren")
	public String aktivieren (UUID id) {
		verwaltung.aktivieren(id);
		return "redirect:";
	}

	@GetMapping("{id}")
	public String zeigeAngestellten (Model model, @RequestParam("id") Angestellter angestellter) {
		model.addAttribute("angestellter", angestellter);
		return PATH + "detail";
	}


}
