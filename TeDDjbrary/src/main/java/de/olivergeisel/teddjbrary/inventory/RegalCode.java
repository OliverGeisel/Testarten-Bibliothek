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

import jakarta.persistence.Embeddable;

@Embeddable
public record RegalCode(String prefix, int number, String suffix) {
	public RegalCode {
		if (prefix == null || suffix == null) {
			throw new IllegalArgumentException("prefix or suffix must not be null");
		}
		if (number < 0) {
			throw new IllegalArgumentException("number must not be negative");
		}
	}

	public RegalCode(String prefix, int number) {
		this(prefix, number, "");
	}

	public RegalCode() {
		this("", 0, "");
	}

	public String toString() {
		if (suffix.isEmpty()) {
			return prefix + "-" + number;
		}
		return prefix + "-" + number + "-" + suffix;
	}
}
