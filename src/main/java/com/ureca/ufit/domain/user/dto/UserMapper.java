package com.ureca.ufit.domain.user.dto;

import com.ureca.ufit.domain.user.dto.request.RegisterRequest;
import com.ureca.ufit.entity.User;

public class UserMapper {
	public static User toEntity(RegisterRequest loginRequest, String encodePassword) {
		return User.of(loginRequest.email(),
			encodePassword,
			loginRequest.age(),
			loginRequest.family(),
			loginRequest.gender(),
			loginRequest.role(),
			loginRequest.ratePlanId());
	}
}
