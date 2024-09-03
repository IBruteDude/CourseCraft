package com.coursecraft.entity;

import com.coursecraft.dto.SignupDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "new_admin_request")
@NoArgsConstructor
public class NewAdminRequest extends User {

	public NewAdminRequest(SignupDto signupDto) {
		super(signupDto);
	}

}
