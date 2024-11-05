package de.olivergeisel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ControllerUnitTest {

	private MyController controller;

	@Mock
	private MyService service;


	@BeforeEach
	void setup() {
		controller = new MyController(service);
	}

	@Test
	void createEntity() {
		var entity = new MyEntity("Test");
		when(service.save(any())).thenReturn(entity);

		var result = controller.createEntity(entity);

		verify(service).save(entity);
		assertEquals("entity created: ", result);
	}
}
