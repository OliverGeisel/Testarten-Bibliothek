package de.olivergeisel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Template für Unit-Tests mit JUnit5 und Mockito
 */
class UnitTestTemplate {

	// def der privaten attribute die zum test gebraucht werden.
	private MeineKlasse getestetsObjekt;

	// Mock-Objekte
	@Mock
	private AndereKlasse mockObjekt;

	@BeforeEach
	void setup() {
		// init der privaten Attribute
		MockitoAnnotations.openMocks(this);
		getestetsObjekt = new MeineKlasse(mockObjekt);

		// eventuelles Stubbing
		when(getestetsObjekt.isX()).thenReturn(true);
	}

	@AfterEach
	void tearDown() {
		// Optionaler Reset von Objekten.
	}

	@Test
	void meineTestMethode1() {
		getestetsObjekt.m1();
		assertTrue(getestetsObjekt.isGut(), "Nach m1 muss das Objekt als gut gelten!");
	}

	@Test
	void meineTestMethode2() {
		assertEquals(mockObjekt, getestetsObjekt.getAndereKlasse(), "Der Getter muss das passende Objekt zurückgeben");
	}

	@Test
	void meineTestMethode3() {
		try {
			getestetsObjekt.methodeMitException();
			fail("Die Exception muss ausgelöst werden");
		} catch (NoSuchElementException ne) {
			// Alles Okay
		}
	}
}