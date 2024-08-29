package com.coursecraft.controller;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import com.coursecraft.util.UserDispatcher;

@Controller
public class HomeController {

	@Autowired
	private UserDispatcher<String> userDispatcher;

	@GetMapping("/")
	public void root(@Autowired HttpServletResponse response) throws IOException {
		response.sendRedirect("/dashboard");
	}

	@GetMapping("/dashboard")
	public String dashboard(@CookieValue(name = "sessionId", required = false) String sessionId) {
		try {
			return userDispatcher
					.sessionId(sessionId)
					.admin("/admin/dashboard.html")
					.instructor("/instructor/dashboard.html")
					.student("/student/dashboard.html")
					.notFound("redirect:/login")
					.select();
		} catch (jakarta.persistence.NoResultException e) {
			return "/error";
		}
	}

}
