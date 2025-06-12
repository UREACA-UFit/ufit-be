package com.ureca.ufit.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ureca.ufit.domain.user.dto.request.RegisterRequest;
import com.ureca.ufit.domain.user.dto.response.RegisterResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User API", description = "회원 관련 API")
@RequestMapping("/api")
public interface UserControllerApiSpec {

	@Operation(
		summary = "회원가입",
		description = "신규 사용자를 등록한다."
	)
	@ApiResponse(
		responseCode = "201",
		description = "회원가입 성공",
		content = @Content(schema = @Schema(implementation = RegisterResponse.class))
	)
	@PostMapping("/users/register")
	public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest);

}
