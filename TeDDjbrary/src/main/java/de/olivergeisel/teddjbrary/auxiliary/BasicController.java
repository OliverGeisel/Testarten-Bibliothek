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

package de.olivergeisel.teddjbrary.auxiliary;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
public class BasicController {

	private final BasicService basicService;

	public BasicController(BasicService basicService) {
		this.basicService = basicService;
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
	String addTestimonial(@RequestParam String content, @RequestParam String from, @RequestParam String role,
			Model model) {
		basicService.createPost(content, from, role);
		model.addAttribute("posts", basicService.getAllPosts());
		return "redirect:/testimonials";
	}

	@GetMapping("/geheim")
	public String geheim() {
		return "geheim";
	}
}
