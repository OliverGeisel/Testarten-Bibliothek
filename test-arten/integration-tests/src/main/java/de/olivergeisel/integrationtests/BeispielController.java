package de.olivergeisel.integrationtests;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BeispielController {


	@GetMapping("/beispiel")
	public String beispiel() {
		return "template1";
	}

	@PostMapping("/besonders")
	public String besonders() {
		return "template1";
	}

	@GetMapping("/redirect")
	public String redirect() {
		return "redirect:/beispiel";
	}

	@GetMapping("/form")
	public String beispiel2(BeispielForm form, Model model) {
		model.addAttribute("form", form);
		// mach manches mit form
		return "template2";
	}


	// Geschützte Bereiche

	@PreAuthorize("hasRole('ADMIN') and isAuthenticated()")
	@GetMapping("/geschuetzt")
	public String geschuetzt() {
		return "template1";
	}
}
