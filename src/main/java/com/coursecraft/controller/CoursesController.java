package com.coursecraft.controller;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.coursecraft.dao.AppDao;
import com.coursecraft.dto.RecommendationsDto;
import com.coursecraft.dto.SearchDto;
import com.coursecraft.entity.Course;
import com.coursecraft.entity.Instructor;
import com.coursecraft.entity.Student;
import com.coursecraft.entity.User;
import com.coursecraft.util.RecommendationEngine;
import com.coursecraft.util.SearchEngine;
import com.coursecraft.util.UserDispatcher;
import com.coursecraft.util.UserDispatcher.MissingHandlerException;

@RestController
public class CoursesController {

	@Autowired
	private AppDao appDao;
	@Autowired
	private UserDispatcher<Supplier<?>> userDispatcher;
	@Autowired
	private SearchEngine searchEngine;
	@Autowired
	private RecommendationEngine recommandationEngine;

	@GetMapping("/user/courses")
	public ModelAndView userCoursesGetter(
			@CookieValue(required = false) String adminSessionId,
			@CookieValue(required = false) String instructorSessionId,
			@CookieValue(required = false) String studentSessionId) {
		return (ModelAndView) userDispatcher
				.instructorSessionId(instructorSessionId)
				.studentSessionId(studentSessionId)
				.instructor(() -> new ModelAndView(new RedirectView("/instructor/created_courses.html"), "instructor",
						(Instructor) appDao.findBySessionId(instructorSessionId).get()))
				.student(() -> new ModelAndView(new RedirectView("/student/purchased_courses.html"), "student",
						(Student) appDao.findBySessionId(studentSessionId).get()))
				.notFound(() -> new ModelAndView(new RedirectView("/login")))
				.select().get();
	}

	@GetMapping("/courses/{courseName}")
	public ModelAndView coursePageGetter(@PathVariable(name = "courseName") String courseName,
			@CookieValue(required = false) String adminSessionId,
			@CookieValue(required = false) String instructorSessionId,
			@CookieValue(required = false) String studentSessionId) {
		ModelAndView mv = new ModelAndView();
		List<Course> courses = appDao.queryWith(Course.class, "name", courseName, 1);
		if (courses.isEmpty()) {
			mv.setViewName("not_found.html");
			mv.addObject("resource", "course");
			return mv;
		}
		mv.setViewName("course_page.html");
		mv.addObject("course", courses.getFirst());
		return mv;
	}

	@GetMapping("/api/v1/courses/search")
	public ResponseEntity<?> search(
			@RequestBody SearchDto searchDto,
			@CookieValue(required = false) String adminSessionId,
			@CookieValue(required = false) String instructorSessionId,
			@CookieValue(required = false) String studentSessionId) {
		return ResponseEntity.ok(searchEngine.getSearchResults(appDao, searchDto));
	}

	@GetMapping("/api/v1/courses/recommendations")
	public ResponseEntity<?> recommendations(
			@CookieValue(required = false) String sessionId,
			@RequestBody RecommendationsDto recommendationsParams) {
		return ResponseEntity
				.ok(recommandationEngine.getRecommendations(appDao.findBySessionId(sessionId), recommendationsParams,
						12));
	}

	@PostMapping("/api/v1/courses/pending/{courseId}/reject")
	public ResponseEntity<?> adminReject(
			@PathVariable Integer courseId,
			@CookieValue(required = false) String adminSessionId,
			@CookieValue(required = false) String instructorSessionId,
			@CookieValue(required = false) String studentSessionId) {
		try {
			Optional<User> user = (Optional<User>) userDispatcher.adminSessionId(adminSessionId)
					.admin(() -> appDao.findBySessionId(adminSessionId)).select().get();
			Course course = appDao.findById(Course.class, courseId);

			course.setStatus(Course.Status.REJECTED);
			appDao.save(course);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (MissingHandlerException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

	}

	@PostMapping("/api/v1/courses/pending/{courseId}/accept")
	public ResponseEntity<?> adminAccept(
			@PathVariable Integer courseId,
			@CookieValue(required = false) String adminSessionId,
			@CookieValue(required = false) String instructorSessionId,
			@CookieValue(required = false) String studentSessionId) {
		try {
			Optional<User> user = (Optional<User>) userDispatcher.adminSessionId(adminSessionId)
					.admin(() -> appDao.findBySessionId(adminSessionId)).select().get();
			Course course = appDao.findById(Course.class, courseId);

			course.setStatus(Course.Status.LIVE);
			appDao.save(course);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (MissingHandlerException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

}
