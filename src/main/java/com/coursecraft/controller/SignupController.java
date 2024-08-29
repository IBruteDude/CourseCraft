package com.coursecraft.controller;

import com.coursecraft.dao.AppDao;
import com.coursecraft.dto.SignupDto;
import com.coursecraft.entity.Admin;
import com.coursecraft.entity.Country;
import com.coursecraft.entity.Instructor;
import com.coursecraft.entity.Student;
import com.coursecraft.entity.User;
import com.coursecraft.entity.UserSession;
import com.coursecraft.entity.User.Role;

import java.io.IOException;
import java.util.*;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.MergedAnnotation.Adapt;
import org.springframework.http.*;
import org.springframework.http.ResponseCookie.ResponseCookieBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class SignupController {

	@Autowired
	AppDao appDao;

	@GetMapping(path = "/signup")
	public ModelAndView signupGetter() {
		ModelAndView mv = new ModelAndView("/signup.html");
		mv.addObject("countries", Country.values());
		return mv;
	}

	@PostMapping(path = "/signup", consumes = "text/html", produces = "text/html")
	public ModelAndView signupPosterHtml(
			@RequestParam(name = "email") String email,
			@RequestParam(name = "password") String password,
			@RequestParam(name = "role") String role,
			@RequestParam(name = "firstName") String firstName,
			@RequestParam(name = "lastName") String lastName,
			@RequestParam(name = "country") String country) {
		if (handleSignUp(new SignupDto(email, password, country, firstName, lastName, country)))
			return new ModelAndView(new RedirectView("/login"));
		return new ModelAndView(new RedirectView("/signup?registrationFailure=true"));
	}

	@PostMapping(path = "/signup", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> signupPosterJson(@RequestBody SignupDto signupDto) {
		if (handleSignUp(signupDto)) {
			Map<String, String> success = new HashMap<>();
			success.put("success", "account created successfully");
			return ResponseEntity.ok(success);
		} else {
			Map<String, String> error = new HashMap<>();
			error.put("error", "invalid parameters");
			return ResponseEntity.badRequest().body(error);
		}
	}

	private boolean handleSignUp(SignupDto signupDto) {
		System.out.println(signupDto);
		return null != appDao.save(switch (Role.valueOf(signupDto.role)) {
			case Role.ADMIN -> new Admin(signupDto);
			case Role.STUDENT -> new Student(signupDto);
			case Role.INSTRUCTOR -> new Instructor(signupDto);
			default -> null;
		});
	}

}
