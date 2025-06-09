package com.ureca.ufit.global.auth.handler;

import static com.ureca.ufit.global.auth.util.JwtUtil.AUTH_HEADER;
import static com.ureca.ufit.global.auth.util.JwtUtil.BEARER_PREFIX;
import static com.ureca.ufit.global.auth.util.JwtUtil.REFRESH_TOKEN_EXPIRED_MS;

import com.ureca.ufit.entity.RefreshToken;
import com.ureca.ufit.global.auth.details.CustomUserDetails;
import com.ureca.ufit.global.auth.repository.RefreshTokenRepository;
import com.ureca.ufit.global.auth.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final SecretKey secretKeyKey;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {

        // 로그인 성공 시 인증 객체의 principal을 정의하기 위한 유저 정보
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // 로그인 성공 시 어세스/리프레시 토큰 발급
        String accessToken = JwtUtil.createAccessToken(userDetails.email(), secretKeyKey);
        String refreshToken = JwtUtil.createRefreshToken(userDetails.email(), secretKeyKey);

        // 레디스에 리프레시 토큰 저장
        RefreshToken refreshTokenEntity = RefreshToken.of(refreshToken, userDetails.email());
        refreshTokenRepository.save(refreshTokenEntity);

        // 쿠키에 리프레시 토큰 저장 (timeout 3일)
        JwtUtil.setRefreshTokenCookie(response, refreshToken, REFRESH_TOKEN_EXPIRED_MS /1000);

        // 헤더에 어세스 토큰 저장
        response.setHeader(AUTH_HEADER, BEARER_PREFIX + accessToken);
    }

}
