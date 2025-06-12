package com.ureca.ufit.domain.user.dto.request;

public record LoginRequest(
	String email,
	String password
) {
}
