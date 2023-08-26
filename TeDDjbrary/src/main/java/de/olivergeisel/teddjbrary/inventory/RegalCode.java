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
