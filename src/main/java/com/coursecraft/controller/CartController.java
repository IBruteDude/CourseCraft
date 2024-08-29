package com.coursecraft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CartController {

	@GetMapping("/user/cart")
	public String cart(
			@CookieValue(name = "sessionId", required = false) String sessionId,
			@RequestBody String body) {
		return "";
	}

}
