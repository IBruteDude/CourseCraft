package com.coursecraft.controller;

import com.coursecraft.dao.AppDao;
import com.coursecraft.dto.LoginDto;
import com.coursecraft.entity.User;
import com.coursecraft.entity.UserSession;

import java.io.IOException;
import java.util.List;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.ResponseCookie.ResponseCookieBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

	@Autowired
	AppDao appDao;

	@GetMapping(path = "/login")
	public String loginPage() {
		return "/login.html";
	}

	@PostMapping(path = "/login")
	public ResponseEntity<?> handleLogin(
			@RequestParam(name = "email") String email,
			@RequestParam(name = "password") String password,
			@RequestParam(name = "savePassword", required = false) String savePassword) throws IOException {
		LoginDto loginInfo = new LoginDto(email, password);
		List<User> users = appDao.queryWith(User.class, "email", loginInfo.getEmail(), 1);

		if (users.isEmpty())
			return ResponseEntity
					.status(HttpStatus.SEE_OTHER)
					.header(HttpHeaders.LOCATION, "/login?success=false")
					.build();

		User user = users.getFirst();

		if (!loginInfo.getPassword().equals(user.getPassword()))
			return ResponseEntity
					.status(HttpStatus.SEE_OTHER)
					.header(HttpHeaders.LOCATION, "/login?success=false")
					.build();

		UserSession session = new UserSession(user);

		appDao.save(session);

		ResponseCookieBuilder cookie = ResponseCookie.from("sessionId", session.getSessionUuid().toString())
				.httpOnly(true)
				.secure(true)
				.path("/");

		String cookieValue = (savePassword != "on") ? cookie.maxAge(Duration.ofDays(365)).build().toString()
				: cookie.build().toString();

		return ResponseEntity
				.status(HttpStatus.SEE_OTHER)
				.header(HttpHeaders.LOCATION, "/dashboard")
				.header(HttpHeaders.SET_COOKIE, cookieValue)
				.build();
	}

}
