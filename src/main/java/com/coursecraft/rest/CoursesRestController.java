package com.coursecraft.rest;

import com.coursecraft.dao.AppDao;
import com.coursecraft.dto.RecommendationsDto;
import com.coursecraft.dto.SearchDto;
import com.coursecraft.entity.Course;
import com.coursecraft.entity.User;
import com.coursecraft.util.RecommendationEngine;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CoursesRestController {

	@Autowired
	private AppDao appDao;
	@Autowired
	private RecommendationEngine engine;

	@GetMapping("/api/v1/courses/search")
	public ResponseEntity<?> search(
			@CookieValue(name = "sessionId", required = false) String sessionId,
			@RequestBody SearchDto searchDto) {
		return ResponseEntity.ok(appDao.search(Course.class, "courseTitle", searchDto.getSearchQuery(), 12));
	}

	@GetMapping("/api/v1/courses/recommendations")
	public ResponseEntity<?> recommendations(
			@CookieValue(name = "sessionId", required = false) String sessionId,
			@RequestBody RecommendationsDto recommendationsParams) {
		return ResponseEntity.ok(engine.getCourseRecommendations(appDao.findBySessionId(sessionId), recommendationsParams, 12));
	}

	@PostMapping("/api/v1/courses/pending/{courseId}/reject")
	public ResponseEntity<?> adminReject(
			@CookieValue(name = "sessionId", required = false) String sessionId,
			@PathVariable(name = "courseId") Integer courseId) {
		Optional<User> user = appDao.findBySessionId(sessionId);

		if (user.isEmpty())
			return ResponseEntity.notFound().build();
		if (user.get().getRole() != User.Role.ADMIN)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		Course course = appDao.findById(Course.class, courseId);

		course.setStatus(Course.Status.REJECTED);
		appDao.save(course);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PostMapping("/api/v1/courses/pending/{courseId}/accept")
	public ResponseEntity<?> adminAccept(
			@CookieValue(name = "sessionId", required = false) String sessionId,
			@PathVariable(name = "courseId") Integer courseId) {
		Optional<User> user = appDao.findBySessionId(sessionId);

		if (user.isEmpty())
			return ResponseEntity.notFound().build();
		if (user.get().getRole() != User.Role.ADMIN)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		Course course = appDao.findById(Course.class, courseId);

		course.setStatus(Course.Status.LIVE);
		appDao.save(course);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
