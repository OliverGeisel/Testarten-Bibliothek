package de.olivergeisel.teddjbrary;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingController {
	@GetMapping("/")
	ResponseEntity<String > landing(){
		return ResponseEntity.ok("Welcome to the library");
	}

	@GetMapping("hello")
	String hello(){
		return "hello";
	}
}
