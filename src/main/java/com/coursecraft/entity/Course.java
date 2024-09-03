package com.coursecraft.entity;

import java.time.LocalDateTime;
import java.net.URI;
import java.util.Set;
import java.util.HashSet;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "course")
@NoArgsConstructor
@Getter
@Setter
public class Course {

	public static enum Status {
		PENDING, REJECTED, LIVE
	}

	public Course(String courseName, String courseTitle, String description, String category, Double price,
			Language language, Instructor instructor, @Nullable URI coursePictureUri) {
		this.name = courseName;
		this.title = courseTitle;
		this.description = description;
		this.category = category;
		this.price = price;
		this.language = language;
		this.coursePictureUri = coursePictureUri;
		this.instructor = instructor;

		this.createdAt = LocalDateTime.now();
		this.updatedAt = this.createdAt;
		this.students = new HashSet<>();
		this.status = Status.PENDING;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;

	@Column(name = "name", nullable = false)
	protected String name;

	@Column(name = "title", nullable = false)
	protected String title;

	@Column(name = "description", nullable = false)
	protected String description;

	@Column(name = "category", nullable = false)
	protected String category;

	@Column(name = "price", nullable = false)
	protected Double price = 0.0;

	@Column(name = "course_picture_uri")
	protected URI coursePictureUri;

	@Column(name = "created_at")
	protected LocalDateTime createdAt;

	@Column(name = "updated_at")
	protected LocalDateTime updatedAt;

	@ManyToMany(mappedBy = "courses")
	protected Set<Student> students;

	@ManyToOne
	@JoinColumn(name = "instructor_id", nullable = false)
	protected Instructor instructor;

	@Enumerated
	protected Language language;

	@Enumerated
	protected Status status;

}
