package com.coursecraft.entity;

import java.util.Set;

import com.coursecraft.dto.SignupDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "instructor")
@NoArgsConstructor
public class Instructor extends User {

	public Instructor(String email, String password, String firstName, String lastName, String country) {
		super(email, password, firstName, lastName, country);
		role = User.Role.INSTRUCTOR;
	}

	public Instructor(SignupDto signupDto) {
		super(signupDto);
		role = User.Role.INSTRUCTOR;
	}

	@OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true)
	protected Set<Course> createdCourses;

}
