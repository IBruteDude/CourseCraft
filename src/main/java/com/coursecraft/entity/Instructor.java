package com.coursecraft.entity;

import java.util.Set;

import com.coursecraft.constant.Authority;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "instructor")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Instructor extends User {

	public Authority getAuthority() {
		return Authority.INSTRUCTOR;
	}

	@OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true)
	protected Set<Course> createdCourses;

}
