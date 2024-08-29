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

/*

	@Mapping("")
	public String (
	@CookieValue(name = "sessionId", required = false) String sessionId,
	@RequestBody String body) {
	return "";
	}

GET url: /courses/{courseName} | cookies: sessionId

GET url: /api/v1/courses/search | cookie: sessionId? | json: { "searchQuery": string }

GET url: /api/v1/courses/recommendations | cookie: sessionId? | json: { "filters"?: { (<filterKey>: Value)* }, "sortBy"?: string }

POST url: /api/v1/courses/<id>/cart | cookies: sessionId

POST url: /api/v1/courses/<id>/cart/checkout | cookies: sessionId

GET url: /user/courses | cookies: sessionId

POST url: /api/v1/courses/pending/<id>/reject | cookies: sessionId

POST url: /api/v1/courses/pending/<id>/accept | cookies: sessionId

POST url: /api/v1/courses/pending/<id>/edit | cookies: sessionId

GET url: /user/courses | cookies: sessionId

GET url: /user/courses | cookies: sessionId

GET url: /api/v1/courses/<id>/save | cookies: sessionId

GET url: /user/courses | cookies: sessionId?

GET url: /user/courses | cookies: sessionId?

GET url: /user/cart | cookies: sessionId?

DELETE url: /api/v1/course/<id> | cookies: sessionId?

PUT url: /api/v1/user/profile

GET url: /user/courses/create

GET url: /user/courses/<courseName>/edit

POST url: /user/courses/create (resp -> redirect on success, json on failure)

POST url: /api/v1/user/courses/<id>/modules/ | cookies: sessionId? | json: { "title" : <module title> }

DELETE url: /api/v1/user/courses/<id>/modules/<id> | cookies: sessionId?

POST url: /api/v1/user/courses/<id>/modules/<id>/lesson | cookies: sessionId? | json: { "title" : <lesson title> }

PUT url: /api/v1/user/courses/<id>/modules/<id>/lesson/<id> | cookies: sessionId? | json: { <lessonObject> }

DELETE url: /api/v1/user/courses/<id>/modules/<id>/lesson/<id> | cookies: sessionId?

*/
