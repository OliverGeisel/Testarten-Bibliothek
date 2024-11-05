package de.olivergeisel.teddjbrary.inventory;

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
@Tag("component")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class BestandsControllerIntegrationTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	RegalRepository regalRepository;

	@Test
	void overviewGet_okay() throws Exception {
		mockMvc.perform(get("/inventory"))
			   .andExpect(status().isOk())
			   .andExpect(view().name("inventory"));
	}

	@Test
	void detailGet_okay() throws Exception {
		var id = regalRepository.findAll().stream().findFirst().orElseThrow().alleBuecher().getFirst().getId();
		mockMvc.perform(get("/inventory/" + id))
			   .andExpect(status().isOk())
			   .andExpect(view().name("inventory-detail"));
	}

	@Test
	void detailsGet_nicht_gefunden() throws Exception {
		mockMvc.perform(get("/inventory/12345678-1234-1234-1234-123456789012"))
			   .andExpect(status().is3xxRedirection())
			   .andExpect(redirectedUrl("/inventory"));
	}

	@Test
	@WithMockUser(username = "besucher", roles = "BESUCHER")
	void ausleihenPost_okay() throws Exception {
		var id = regalRepository.findAll().stream().findFirst().orElseThrow().alleBuecher().getFirst().getId();
		mockMvc.perform(post("/inventory/ausleihen/" + id))
			   .andExpect(status().is3xxRedirection());
	}

	@Test
	@WithMockUser(username = "besucher", roles = "BESUCHER")
	void ausleihen_mit_falschen_Nutzer() throws Exception {
		// Diese Anfrage führt zu einer 400, da der Benutzer nicht vorhanden ist
		var id = "Ungültige ID";
		mockMvc.perform(post("/inventory/ausleihen/" + id))
			   .andExpect(status().isBadRequest());
	}

	// Besondere Fälle als Beispiele

	@Test
	void overviewPost_nicht_erlaubt() throws Exception {
		mockMvc.perform(post("/inventory"))
			   .andExpect(status().isMethodNotAllowed()); // 405
	}

	@Test
	void inventoryGet_nicht_gefunden() throws Exception {
		mockMvc.perform(get("/inventory/test/12345678-1234-1234-1234-123456789012"))
			   .andExpect(status().isNotFound()); // 404
	}

	@Test
	void ausleihenPost_unauthorisiert() throws Exception {
		// fehlende Authentifizierung kein User --> eigentlich 401 aber wird zu 302
		var id = regalRepository.findAll().stream().findFirst().orElseThrow().alleBuecher().getFirst().getId();
		mockMvc.perform(post("/inventory/ausleihen/" + id))
			   .andExpect(status().is3xxRedirection());
	}

}