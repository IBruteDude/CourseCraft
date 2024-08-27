package com.coursecraft.controllers;

import com.coursecraft.daos.AppDao;
import com.coursecraft.dtos.LoginDto;
import com.coursecraft.entities.User;
import com.coursecraft.entities.UserSession;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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
			// @RequestBody String body,
			@Autowired HttpServletResponse response) throws IOException {

		LoginDto loginInfo = new LoginDto(email, password);

		List<User> users = appDao.queryWith(User.class, "email", loginInfo.getEmail(), 1);

		if (users.isEmpty()) {
			response.sendRedirect("/login?success=false");
			return ResponseEntity.notFound().build();
		}
		User user = users.getFirst();

		if (!loginInfo.getPassword().equals(user.getPassword())) {
			response.sendRedirect("/login?success=false");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		UserSession session = new UserSession(user);

		appDao.save(session);

		response.addCookie(new Cookie("sessionId", session.getSessionUuid().toString()));
		response.sendRedirect("/dashboard");
		return ResponseEntity.ok().build();

		// ObjectMapper om = new ObjectMapper();
		// try {
		// LoginDto loginInfo =
		// om.readerWithView(LoginDto.LoginDtoJsonView.class).readValue(body);
		// } catch (JacksonException e) {
		// return ResponseEntity.badRequest().build();
		// }
	}

}
