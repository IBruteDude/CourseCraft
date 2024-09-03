package com.coursecraft.controller;

import com.coursecraft.dao.AppDao;
import com.coursecraft.dto.LoginDto;
import com.coursecraft.entity.User;
import com.coursecraft.entity.UserSession;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginController {

	@Autowired
	AppDao appDao;

	@GetMapping(path = "/login")
	public String loginPage() {
		return "/login.html";
	}

	@PostMapping(path = "/login", consumes = "application/x-www-form-urlencoded", produces = "text/html")
	public View login(
			@Autowired HttpServletResponse response,
			@RequestParam String email,
			@RequestParam String password,
			@RequestParam(required = false) String savePassword) {
		LoginDto loginInfo = new LoginDto(email, password);
		List<User> users = appDao.queryWith(User.class, "email", loginInfo.email, 1);

		if (users.isEmpty())
			return new RedirectView("/login?success=false");

		User user = users.getFirst();

		if (!loginInfo.password.equals(user.getPassword()))
			return new RedirectView("/login?success=false");

		int maxAge = ((savePassword == "on") ? 365 : 1) * 24 * 60 * 60;
		UserSession session = new UserSession(user, maxAge);

		appDao.save(session);

		Cookie cookie = new Cookie(user.getRole().toString().toLowerCase() + "SessionId",
				session.getSessionUuid().toString());
		cookie.setSecure(true);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
		return new RedirectView("/dashboard");
	}

	@PostMapping(path = "/logout")
	public View logout(
			@CookieValue(required = false) String adminSessionId,
			@CookieValue(required = false) String instructorSessionId,
			@CookieValue(required = false) String studentSessionId) {
		List<UserSession> adminSessions = appDao.queryWith(UserSession.class, "sessionUuid", adminSessionId, 1);
		if (!adminSessions.isEmpty())
			adminSessions.getFirst().setExpired(true);
		List<UserSession> instructorSessions = appDao.queryWith(UserSession.class, "sessionUuid", instructorSessionId,
				1);
		if (!instructorSessions.isEmpty())
			instructorSessions.getFirst().setExpired(true);
		List<UserSession> studentSessions = appDao.queryWith(UserSession.class, "sessionUuid", studentSessionId, 1);
		if (!studentSessions.isEmpty())
			studentSessions.getFirst().setExpired(true);
		return new RedirectView("/login");
	}

}
