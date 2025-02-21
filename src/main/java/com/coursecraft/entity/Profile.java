package com.coursecraft.entity;

import com.coursecraft.constant.Authority;
import com.coursecraft.constant.Country;

import jakarta.persistence.*;

public class Profile {

	@Column(name = "first_name")
	protected String firstName;

	@Column(name = "last_name")
	protected String lastName;

	@Column(name = "profile_picture_uri")
	protected String profilePictureUri;

	@Enumerated
	protected Country country;

	@Enumerated
	protected Authority authority;


	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

}
