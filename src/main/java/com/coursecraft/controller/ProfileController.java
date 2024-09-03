package com.coursecraft.controller;

import java.util.HashMap;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.coursecraft.dao.AppDao;
import com.coursecraft.dto.ProfileDto;
import com.coursecraft.util.UserDispatcher;
import com.coursecraft.util.UserDispatcher.MissingHandlerException;

@RestController
public class ProfileController {

	@Autowired
	private AppDao appDao;
	@Autowired
	UserDispatcher<Supplier<?>> userDispatcher;

	@GetMapping("/profile")
	public View profileGetter(
			@CookieValue(required = false) String adminSessionId,
			@CookieValue(required = false) String instructorSessionId,
			@CookieValue(required = false) String studentSessionId) {
		return new RedirectView((String) userDispatcher
				.adminSessionId(adminSessionId)
				.instructorSessionId(instructorSessionId)
				.studentSessionId(studentSessionId)
				.admin(() -> "/admin/profile.html")
				.instructor(() -> "/instructor/profile.html")
				.student(() -> "/student/profile.html")
				.notFound(() -> "/login")
				.select().get());
	}

	@PutMapping(path = "/profile", consumes = "application/json", produces = "application/json")
	/**
	 * To Be checked
	 **/
	public ResponseEntity<?> profilePutterJson(@RequestBody ProfileDto profileDto,
			@CookieValue(required = false) String adminSessionId,
			@CookieValue(required = false) String instructorSessionId,
			@CookieValue(required = false) String studentSessionId) {
		try {
			userDispatcher
					.adminSessionId(adminSessionId)
					.instructorSessionId(instructorSessionId)
					.studentSessionId(studentSessionId)
					.admin(() -> appDao.findBySessionId(adminSessionId).get().update(profileDto))
					.instructor(() -> appDao.findBySessionId(instructorSessionId).get().update(profileDto))
					.student(() -> appDao.findBySessionId(studentSessionId).get().update(profileDto))
					.select();
			HashMap<String, String> success = new HashMap<>();
			success.put("success", "profile updated successfully");
			return ResponseEntity.ok(success);
		} catch (MissingHandlerException e) {
			HashMap<String, String> error = new HashMap<>();
			error.put("error", "user not registered");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
		}

	}

}
