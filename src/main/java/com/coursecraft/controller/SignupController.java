package com.coursecraft.controller;

import com.coursecraft.dao.AppDao;
import com.coursecraft.dto.SignupDto;
import com.coursecraft.entity.Country;
import com.coursecraft.entity.Instructor;
import com.coursecraft.entity.NewAdminRequest;
import com.coursecraft.entity.Student;
import com.coursecraft.entity.User;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class SignupController {

	@Autowired
	AppDao appDao;

	@GetMapping(path = "/signup")
	public ModelAndView signupGetter() {
		ModelAndView mv = new ModelAndView("/signup.html");
		mv.addObject("countries", Country.values());
		return mv;
	}

	@PostMapping(path = "/signup", consumes = "application/x-www-form-urlencoded", produces = "text/html")
	public View signupPosterHtml(
			@RequestParam String email,
			@RequestParam String password,
			@RequestParam String role,
			@RequestParam String firstName,
			@RequestParam String lastName,
			@RequestParam String country) {
		if (!appDao.queryWith(User.class, "email", email, 1).isEmpty())
			return new RedirectView("/signup?alreadyRegistered=true");
		if (registerUser(new SignupDto(email, password, role, firstName, lastName, country)))
			return new RedirectView("/login");
		return new RedirectView("/signup?registrationFailure=true");
	}

	@PostMapping(path = "/signup", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> signupPosterJson(@RequestBody SignupDto signupDto) {
		if (!appDao.queryWith(User.class, "email", signupDto.email, 1).isEmpty()) {
			Map<String, String> error = new HashMap<>();
			error.put("error", "user already exists");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
		}
		if (registerUser(signupDto)) {
			Map<String, String> success = new HashMap<>();
			success.put("success",
					(User.Role.valueOf(signupDto.role) == User.Role.ADMIN) ? "admin request created successfully"
							: "account created successfully");

			return ResponseEntity.ok(success);
		}
		Map<String, String> error = new HashMap<>();
		error.put("error", "invalid parameters");
		return ResponseEntity.badRequest().body(error);
	}

	private boolean registerUser(SignupDto signupDto) {
		return null != appDao.save(switch (User.Role.valueOf(signupDto.role)) {
			case User.Role.ADMIN -> new NewAdminRequest(signupDto);
			case User.Role.STUDENT -> new Student(signupDto);
			case User.Role.INSTRUCTOR -> new Instructor(signupDto);
			default -> null;
		});
	}

}
