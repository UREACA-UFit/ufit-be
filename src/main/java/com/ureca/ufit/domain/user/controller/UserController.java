package com.ureca.ufit.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ureca.ufit.domain.user.dto.request.RegisterRequest;
import com.ureca.ufit.domain.user.dto.response.RegisterResponse;
import com.ureca.ufit.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController implements UserControllerApiSpec {

	private final UserService userService;

	@Override
	public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
		return ResponseEntity.ok(userService.register(registerRequest));
	}

}
