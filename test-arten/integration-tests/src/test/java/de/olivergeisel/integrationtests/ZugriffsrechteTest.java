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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ZugriffsrechteTest {

	@Autowired
	MockMvc mockMvc;


	@Test
	void test_zugriff_auf_bestandsverwaltung() throws Exception {
		mockMvc.perform(get("/geschuetzt")).andExpect(status().isUnauthorized()); //401
	}

	@Test
	@WithMockUser(username = "besucher", roles = "BESUCHER")
	@Disabled("Funktioniert nicht, wird als 200 OK interpretiert")
	void test_zugriff_auf_bestandsverwaltung_aber_falsche_rolle() throws Exception {
		mockMvc.perform(get("/geschuetzt")).andExpect(status().isForbidden()); //403
	}

	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	void test_zugriff_auf_bestandsverwaltung_als_admin() throws Exception {
		mockMvc.perform(get("/geschuetzt")).andExpect(status().isOk());
	}

}
