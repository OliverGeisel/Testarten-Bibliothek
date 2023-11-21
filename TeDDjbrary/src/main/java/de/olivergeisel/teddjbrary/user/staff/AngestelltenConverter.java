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

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AngestelltenConverter implements Converter<String, Angestellter> {

	private final AngestellterRepository angestellterRepository;

	public AngestelltenConverter (AngestellterRepository angestellterRepository) {
		this.angestellterRepository = angestellterRepository;
	}


	@Override
	public Angestellter convert (String source) {
		return angestellterRepository.findById(UUID.fromString(source)).orElseThrow();
	}
}
