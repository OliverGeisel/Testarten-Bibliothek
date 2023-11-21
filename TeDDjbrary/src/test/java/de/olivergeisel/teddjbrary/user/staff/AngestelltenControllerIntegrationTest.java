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

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Tag("integration")
@Tag("controller")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class AngestelltenControllerIntegrationTest {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	private AngestellterRepository angestelltenrepository;

	@Test
	@WithMockUser(roles = "ADMIN", username = "admin")
	void angestellteOkay () throws Exception {
		mockMvc.perform(get("/angestellte")).andExpectAll(
				status().isOk(),
				view().name("staff/overview"),
				model().attributeExists("angestellte"));
	}

	@Test
	@WithMockUser(roles = "ADMIN", username = "admin")
	void anstellenOkay () throws Exception {

		mockMvc.perform(post("/angestellte/anstellen")
					   .param("vorname", "Max")
					   .param("nachname", "Mustermann")
					   .param("email", "test@tebid.de")
					   .param("alter", "20")
					   .param("geschlecht", "MAENNLICH")
					   .param("passwort", "12345")
					   .param("rolle", "REINIGUNGSKRAFT"))
			   .andExpectAll(
					   status().is3xxRedirection(),
					   redirectedUrl("/angestellte"));
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void anstellenFehlerPasswortstaerke () throws Exception {
		mockMvc.perform(post("/angestellte/anstellen")
					   .param("vorname", "Max")
					   .param("nachname", "Mustermann")
					   .param("email", "test@tebid.de")
					   .param("alter", "20")
					   .param("geschlecht", "MAENNLICH")
					   .param("passwort", "max"))
			   .andExpectAll(
					   status().is3xxRedirection(),
					   redirectedUrl("/angestellte"));
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void entlassenOkay () throws Exception {
		mockMvc.perform(post("/angestellte/entlassen")
					   .param("id", angestelltenrepository.findAll().iterator().next().getId().toString()))
			   .andExpectAll(
					   status().is3xxRedirection(),
					   redirectedUrl("/angestellte"));
	}


	@Test
		//@WithMockUser(roles = "ADMIN")
	void zeigeAngestellten () throws Exception {
		// Es wird nicht erfolgreich sein. Es ist ein Fehler im Controller.
		var id = angestelltenrepository.findAll().iterator().next().getId();
		mockMvc.perform(get("/angestellte/" + id)).andExpectAll(
				status().isOk(),
				view().name("staff/detail"),
				model().attributeExists("angestellter"));
	}
}