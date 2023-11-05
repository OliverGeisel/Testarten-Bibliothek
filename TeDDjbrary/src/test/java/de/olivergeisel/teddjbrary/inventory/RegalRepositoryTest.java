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

package de.olivergeisel.teddjbrary.inventory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.salespointframework.EnableSalespoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Tag("unit")
@Tag("repository")
@EnableSalespoint
class RegalRepositoryTest {

	@Autowired
	private RegalRepository regale;

	@BeforeEach
	void setUp () {

	}

	@AfterEach
	void tearDown () {
	}

	@Test
	void count () {
		regale.save(new Regal(3, 4, new RegalCode()));
		assertEquals(1, regale.count());
	}

	@Test
	void findAll () {
	}

	@Test
	void findByInhalt_Buecher_Isbn () {
	}

	@Test
	void getNichtVolleRegale () {
	}

	@Test
	void findByBuchId () {
	}
}