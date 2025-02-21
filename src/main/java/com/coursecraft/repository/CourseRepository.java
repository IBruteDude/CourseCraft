package com.coursecraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.coursecraft.entity.Course;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface CourseRepository extends JpaRepository<Course, UUID> {

	@Query("SELECT c FROM Course c WHERE c.name = :name")
	Optional<Course> findByName(String name);

	@Query("SELECT c FROM Course c WHERE c.title LIKE '%:title%'")
	List<Course> findByMatchingTitle(String title);

	@Query("SELECT c FROM Course c ORDER BY FUNCTION('RAND') LIMIT :limit")
	List<Course> getRandomRecommendations(int limit);

}
