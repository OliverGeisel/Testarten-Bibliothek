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

package de.olivergeisel.teddjbrary;

import de.olivergeisel.teddjbrary.auxillary.BasicService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Random;

@Controller
public class LandingController {

	private final BasicService basicService;

	public LandingController(BasicService basicService) {
		this.basicService = basicService;
	}

	@GetMapping({"", "/", "/index", "/index.html"})
	String index() {
		return "index";
	}

	@GetMapping({"/text", "/text.html"})
	ResponseEntity<String> landing() {
		return ResponseEntity.ok("Welcome to the library");
	}

	@GetMapping("testimonials")
	String testimonials(HttpServletResponse response, Model model) {
		Random random = new Random();
		var id = random.nextLong(1_000_000_000, 1_000_000_000_000L);
		var cookie = new Cookie("myNewId", Long.toString(id));
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60); // 1 Stunde ; wenn auskommentiert, dann Session-Cookie
		response.addCookie(cookie);
		model.addAttribute("posts", basicService.getAllPosts());
		return "testimonials";
	}

	@PostMapping("testimonials")
	String addTestimonial(@RequestParam String content, @RequestParam String from, @RequestParam String role, Model model) {
		basicService.createPost(content, from, role);
		model.addAttribute("posts", basicService.getAllPosts());
		return "redirect:/testimonials";
	}

	@GetMapping("insecure")
	ResponseEntity<String> unsecure(HttpServletResponse response, HttpServletRequest request) {
		var cookies = request.getCookies();
		var sessioncookie = Arrays.stream(cookies).filter(it -> it.getName().equals("JSESSIONID")).findFirst().orElseThrow();
		sessioncookie.setHttpOnly(false);
		response.addCookie(sessioncookie);
		return ResponseEntity.ok("""
				Insecure!<br>
				🤨🤨🤨(┬┬﹏┬┬)😑😑😑""");
	}

	@GetMapping("secure")
	ResponseEntity<String> secure(HttpServletResponse response, HttpServletRequest request) {
		var cookies = request.getCookies();
		var sessioncookie = Arrays.stream(cookies).filter(it -> it.getName().equals("JSESSIONID")).findFirst().orElseThrow();
		sessioncookie.setHttpOnly(true);
		response.addCookie(sessioncookie);
		return ResponseEntity.ok("""
				Secure!<br>
				🫡🫡🫡(❁´◡`❁)👌👌👌
				""");
	}

	@GetMapping("start")
	public ResponseEntity<String> start() {
		AppConfig.getInstance().setGeheimerUser(true);
		return ResponseEntity.ok("Er liest jetzt🙉🙉!");
	}

	@GetMapping("stop")
	public ResponseEntity<String> stop() {
		AppConfig.getInstance().setGeheimerUser(false);
		return ResponseEntity.ok("Er liest nicht mehr🙈🙈!");
	}

	@GetMapping("hello")
	String hello() {
		return "hello";
	}
}
