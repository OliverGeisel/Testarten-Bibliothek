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

package de.olivergeisel.tdd;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class TelefonbuchController {

	private final TelefonbuchService telefonbuch;

	public TelefonbuchController (TelefonbuchService telefonbuch) {
		this.telefonbuch = telefonbuch;
	}

	@GetMapping("/")
	public String index (Model model) {
		model.addAttribute("entries", telefonbuch.alleEintraege().stream().sorted());
		return "index";
	}

	@GetMapping("/suche")
	String suche (@RequestParam String name, Model model) {
		model.addAttribute("entries", telefonbuch.findeMitNachnamen(name.strip()));
		return "index";
	}

	@PostMapping("/erstellen")
	public String erstellen () {
		return "index";
	}

	@PutMapping("/bearbeitet")
	public String bearbeiten (@RequestParam UUID id, EditForm form) {
		return "index";
	}

	@DeleteMapping("/loeschen")
	public String loeschen (@RequestParam UUID id) {
		telefonbuch.loeschen(id);
		return "index";
	}
}
