package com.ureca.ufit.global.auth.details;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ureca.ufit.entity.enums.Role;

public record CustomUserDetails(
	String email,
	String password,
	Role role

) implements UserDetails {

	private static final String PREFIX_ROLE = "ROLE_";

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(PREFIX_ROLE + role.name()));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

}
