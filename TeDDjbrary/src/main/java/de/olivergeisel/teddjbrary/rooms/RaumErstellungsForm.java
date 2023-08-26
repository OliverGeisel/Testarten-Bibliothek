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

package de.olivergeisel.teddjbrary.rooms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class RaumErstellungsForm {
	@NotBlank
	private String name;
	@Positive
	private int    nummer;

//region setter/getter
	public String getName() {
		return name;
	}

	public int getNummer() {
		return nummer;
	}
//endregion

}
