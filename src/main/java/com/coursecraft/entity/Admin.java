package com.coursecraft.entity;

import com.coursecraft.dto.SignupDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admin")
@NoArgsConstructor
public class Admin extends User {

	public Admin(String email, String password, String firstName, String lastName, String country) {
		super(email, password, firstName, lastName, country);
		role = User.Role.ADMIN;
	}

	public Admin(SignupDto signupDto) {
		super(signupDto);
		role = User.Role.ADMIN;
	}

}
