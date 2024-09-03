package com.coursecraft.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.coursecraft.util.UserDispatcher;

@RestController
public class HomeController {

	@Autowired
	private UserDispatcher<String> userDispatcher;

	@GetMapping("/")
	public View root() throws IOException {
		return new RedirectView("/dashboard");
	}

	@GetMapping("/dashboard")
	public ModelAndView dashboardGetter(
			@CookieValue(required = false) String adminSessionId,
			@CookieValue(required = false) String instructorSessionId,
			@CookieValue(required = false) String studentSessionId) {
		try {
			return new ModelAndView(userDispatcher
					.adminSessionId(adminSessionId)
					.instructorSessionId(instructorSessionId)
					.studentSessionId(studentSessionId)
					.admin("/admin/dashboard.html")
					.instructor("/instructor/dashboard.html")
					.student("/student/dashboard.html")
					.notFound("redirect:/login")
					.select());
		} catch (jakarta.persistence.NoResultException e) {
			return new ModelAndView("/not_found.html", "resource", "user");
		}
	}

}
