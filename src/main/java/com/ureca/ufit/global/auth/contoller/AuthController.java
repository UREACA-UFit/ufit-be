package com.ureca.ufit.global.auth.contoller;

import static com.ureca.ufit.global.auth.util.JwtUtil.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ureca.ufit.global.auth.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/reissue/token")
	public ResponseEntity<Void> reissueToken(
		@CookieValue(name = REFRESH_TOKEN_COOKIE_NAME) String refreshToken,
		@RequestHeader(name = AUTH_HEADER) String bearerToken,
		HttpServletResponse response
	) {
		authService.reissueToken(bearerToken, refreshToken, response);
		return ResponseEntity.ok().build();
	}

}
