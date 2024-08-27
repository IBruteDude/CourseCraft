package com.coursecraft.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student")
@NoArgsConstructor
public class Student extends User {

	public Student(String email, String password) {
		super(email, password);
		role = User.Role.STUDENT;
	}

	public String toString() {
		return (new com.coursecraft.utils.ToStringGenerator(this.getClass())).fieldListString();
	}

}
