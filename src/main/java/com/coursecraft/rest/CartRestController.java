package com.coursecraft.rest;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class CartRestController {

	@PostMapping("/api/v1/user/courses/{courseId}/cart")
	public String addCourseToCast(
			@CookieValue(name = "sessionId", required = false) String sessionId,
			@PathVariable(name = "courseId") String courseId,
			@RequestBody String body) {
		return "";
	}

	@PostMapping("/api/v1/user/cart/checkout")
	public String checkout(
			@CookieValue(name = "sessionId", required = false) String sessionId,
			@RequestBody String body) {
		return "";
	}

}
