package com.coursecraft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.coursecraft.util.UserDispatcher;

@Controller
public class ProfileController {

	@Autowired
	UserDispatcher<String> userDispatcher;

	@GetMapping("/profile")
	public String profilePage(@CookieValue String sessionId) {
		return userDispatcher
				.sessionId(sessionId)
				.admin("/admin/profile.html")
				.instructor("/instructor/profile.html")
				.student("/student/profile.html")
				.notFound("/login")
				.select();
	}

	@PutMapping("/profile")
	public String putMethodName(@RequestBody String entity) {
		return entity;
	}

}
