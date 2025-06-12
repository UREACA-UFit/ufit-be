package com.ureca.ufit.global.auth.handler;

import static com.ureca.ufit.global.auth.util.JwtUtil.*;

import java.io.IOException;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ureca.ufit.domain.user.dto.response.LoginResponse;
import com.ureca.ufit.entity.RefreshToken;
import com.ureca.ufit.global.auth.details.CustomUserDetails;
import com.ureca.ufit.global.auth.repository.RefreshTokenRepository;
import com.ureca.ufit.global.auth.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	private final SecretKey secretKeyKey;
	private final RefreshTokenRepository refreshTokenRepository;
	private final ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {

		// 로그인 성공 시 인증 객체의 principal을 정의하기 위한 유저 정보
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();

		// 로그인 성공 시 어세스/리프레시 토큰 발급
		String accessToken = JwtUtil.createAccessToken(userDetails.email(), secretKeyKey);
		String refreshToken = JwtUtil.createRefreshToken(userDetails.email(), secretKeyKey);

		// 레디스에 리프레시 토큰 저장
		RefreshToken refreshTokenEntity = RefreshToken.of(refreshToken, userDetails.email());
		refreshTokenRepository.save(refreshTokenEntity);

		// 쿠키에 리프레시 토큰 저장 (timeout 3일)
		JwtUtil.updateRefreshTokenCookie(response, refreshToken, REFRESH_TOKEN_EXPIRED_MS / 1000);

		// 헤더에 어세스 토큰 저장
		response.setHeader(AUTH_HEADER, BEARER_PREFIX + accessToken);

		// 바디에 Login Response 저장
		LoginResponse loginResponse = LoginResponse.of(userDetails.getUsername(), userDetails.role());
		objectMapper.writeValue(response.getWriter(), loginResponse);
	}

}
