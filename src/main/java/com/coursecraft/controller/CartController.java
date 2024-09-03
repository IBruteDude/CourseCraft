package com.coursecraft.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CartController {

	@GetMapping("/user/cart")
	public ModelAndView cart(
			@CookieValue(required = false) String sessionId,
			@RequestBody String body,
			@CookieValue(required = false) String adminSessionId,
			@CookieValue(required = false) String instructorSessionId,
			@CookieValue(required = false) String studentSessionId) {
		return new ModelAndView("/student/cart.html");
	}

	@PostMapping("/api/v1/user/courses/{courseId}/cart")
	public ResponseEntity<?> addCourseToCast(
			@PathVariable String courseId,
			@RequestBody String body,
			@CookieValue(required = false) String adminSessionId,
			@CookieValue(required = false) String instructorSessionId,
			@CookieValue(required = false) String studentSessionId) {
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}

	@PostMapping("/api/v1/user/cart/checkout")
	public ResponseEntity<?> checkout(
			@RequestBody String body,
			@CookieValue(required = false) String adminSessionId,
			@CookieValue(required = false) String instructorSessionId,
			@CookieValue(required = false) String studentSessionId) {
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}

}
