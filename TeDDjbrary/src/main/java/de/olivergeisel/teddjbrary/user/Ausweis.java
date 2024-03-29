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


import java.util.Random;

public class Ausweis<P extends Person> {
	private final P   person;
	private final int id;


	public Ausweis(P person) {
		this.person = person;
		id = new Random().nextInt(9000) + 1000;
	}

	//region setter/getter
	public P getPerson() {
		return person;
	}

	public int getId() {
		return id;
	}
//endregion
}
