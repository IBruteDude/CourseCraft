package com.coursecraft.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProfileDto {

	@NotNull
	@NotBlank
	public String password;

	@NotNull
	@NotBlank
	public String firstName;

	@NotNull
	@NotBlank
	public String lastName;

	@NotNull
	@NotBlank
	public String country;

	@jakarta.validation.constraints.Pattern(regexp = """
			(?i)\\b((?:[a-z][\\w-]+:(?:/{1,3}|[a-z0-9%])|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'".,<>?«»“”‘’]))
			""")
	public String profilePictureUri;

	// - Contact Information
	// - associated Cart

}
