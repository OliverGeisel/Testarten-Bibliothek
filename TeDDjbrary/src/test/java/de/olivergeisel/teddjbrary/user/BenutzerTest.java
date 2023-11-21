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

package de.olivergeisel.teddjbrary.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BenutzerTest {


	private Benutzer benutzer;

	@BeforeEach
	void setUp () {
		benutzer = new Benutzer() {
			@Override
			public String getVorname () {
				return "Test";
			}

			@Override
			public String getNachname () {
				return "Benutzer";
			}

			@Override
			public int getAlter () {
				return 42;
			}
		};
	}


	@Test
	void getId () {
	}

	@Test
	void getUserAccount () {
	}

	@Test
	void testEquals () {
	}

	@Test
	void testHashCode () {
	}
}