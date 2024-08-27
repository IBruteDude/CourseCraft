package com.coursecraft.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admin")
@NoArgsConstructor
public class Admin extends User {

	public Admin(String email, String password) {
		super(email, password);
		role = User.Role.ADMIN;
	}

	@Column(name = "Aattr")
	private String Aattr;

	public String toString() {
		return (new com.coursecraft.utils.ToStringGenerator(this.getClass())).fieldListString();
	}

}
