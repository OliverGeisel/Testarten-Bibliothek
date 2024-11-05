package de.olivergeisel.integrationtests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class BeispielForm {

	@NotBlank String name;
	@Email    String email;
	@Positive int    alter;

	//region setter/getter
	public @NotBlank String getName() {
		return name;
	}

	public void setName(@NotBlank String name) {
		this.name = name;
	}

	public @Email String getEmail() {
		return email;
	}

	public void setEmail(@Email String email) {
		this.email = email;
	}

	@Positive
	public int getAlter() {
		return alter;
	}

	public void setAlter(@Positive int alter) {
		this.alter = alter;
	}
//endregion

}
