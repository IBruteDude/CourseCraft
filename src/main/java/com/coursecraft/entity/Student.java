package com.coursecraft.entity;

import java.util.Set;

import com.coursecraft.dto.SignupDto;

import java.util.HashSet;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student")
@NoArgsConstructor
public class Student extends User {

	public Student(String email, String password, String firstName, String lastName, String country) {
		super(email, password, firstName, lastName, country);
		role = User.Role.STUDENT;
	}

	public Student(SignupDto signupDto) {
		super(signupDto);
		role = User.Role.STUDENT;
	}

	@ManyToMany
	@JoinTable(name = "student_course", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private Set<Course> courses = new HashSet<>();

}
