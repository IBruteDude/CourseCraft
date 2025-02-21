package com.coursecraft.entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import com.coursecraft.constant.Authority;

@Entity
@Table(name = "student")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Student extends User {

	public Authority getAuthority() {
		return Authority.STUDENT;
	}

	@ManyToMany
	@JoinTable(name = "student_course", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	@Builder.Default
	private Set<Course> courses = Set.of();

	// TODO: add a field for the Cart entity

}
