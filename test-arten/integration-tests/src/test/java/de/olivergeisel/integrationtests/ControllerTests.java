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

package de.olivergeisel.integrationtests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WithMockUser(username = "admin", roles = {"ADMIN"}, password = "admin")
@WithAnonymousUser()
@WithUserDetails
class ControllerTests {

	MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(BeispielController.class).build();
	}

	@Test
	void test_okay() throws Exception {
		mockMvc.perform(get("/beispiel")).andExpect(status().isOk());
	}

	@Test
	void test_nicht_gefunden() throws Exception {
		mockMvc.perform(get("/nichtgefunden")).andExpect(status().isNotFound());
	}

	@Test
	void test_post_request() throws Exception {
		// Test mit POST-Request auf "/besonders"
		mockMvc.perform(post("/besonders")).andExpect(status().isOk());
	}

	@Test
	void test_get_request_nicht_erlaubt() throws Exception {
		// Test mit GET-Request auf "/besonders" - sollte nicht erlaubt sein
		mockMvc.perform(get("/besonders")).andExpect(status().isMethodNotAllowed());
	}


	@Test
	void test_mit_forms_okay() throws Exception {
		mockMvc.perform(get("/form")
					   .param("name", "Test")
					   .param("email", "test@t.de")
					   .param("alter", "25")
			   )
			   .andExpect(status().isOk());
	}

	@Test
	void test_mit_forms_nicht_okay() throws Exception {
		mockMvc.perform(get("/form")
					   .param("name", "Test")
					   .param("email", "t@t.de")
					   .param("alter", "Keine Nummer") // Nummer kann nicht geparst werden
			   )
			   .andExpect(status().isBadRequest());
	}

	@Test
	void test_mit_umleitung() throws Exception {
		mockMvc.perform(get("/redirect"))
			   .andExpect(status().is3xxRedirection())
			   .andExpect(redirectedUrl("/beispiel"));
	}
}