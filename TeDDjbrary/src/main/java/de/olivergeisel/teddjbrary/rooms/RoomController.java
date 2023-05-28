package de.olivergeisel.teddjbrary.rooms;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("rooms")
public class RoomController {

	private final RoomReposititory repo;

	public RoomController(RoomReposititory repo) {
		this.repo = repo;
	}

	@GetMapping("")
	String overview(Model model){
		model.addAttribute("rooms",repo.findAll());
		return "rooms";
	}

}
