package com.coursecraft.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name="instructor")
@NoArgsConstructor
public class Instructor extends User {
	
    public Instructor(String email, String password) {
		super(email, password);
		role = User.Role.INSTRUCTOR;
	}

	@Column(name = "Iattr")
    private String Iattr;

	public String toString() {
		return (new com.coursecraft.utils.ToStringGenerator(this.getClass())).fieldListString();
	}

}
