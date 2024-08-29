package com.coursecraft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.coursecraft.dao.AppDao;
import com.coursecraft.entity.Course;

@Controller
public class CoursesController {
	@Autowired
	private AppDao appDao;

	@GetMapping("/courses/{courseName} | cookies: sessionId")
	public String getCourse(
			@CookieValue(name = "sessionId", required = false) String sessionId,
			@PathVariable(name = "courseName") String courseName) {
		appDao.queryWith(Course.class, "name", courseName, 1);
		return "";
	}

}
