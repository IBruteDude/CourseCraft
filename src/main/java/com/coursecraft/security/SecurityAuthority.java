package com.coursecraft.security;

import org.springframework.security.core.GrantedAuthority;

import com.coursecraft.constant.Authority;

public class SecurityAuthority implements GrantedAuthority {

	private Authority authority;

	public SecurityAuthority(Authority authority) {
		this.authority = authority; 
	}

	@Override
	public String getAuthority() {
		return "ROLE_" + authority.toString();
	}
	
}
