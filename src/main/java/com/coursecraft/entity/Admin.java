package com.coursecraft.entity;

import com.coursecraft.constant.Authority;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "admin")
@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {

	public Authority getAuthority() {
		return Authority.ADMIN;
	}

}
