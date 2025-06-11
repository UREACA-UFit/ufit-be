package com.ureca.ufit.global.auth.service;

import static com.ureca.ufit.global.auth.util.JwtUtil.AUTH_HEADER;
import static com.ureca.ufit.global.auth.util.JwtUtil.BEARER_PREFIX;
import static com.ureca.ufit.global.auth.util.JwtUtil.BLACKLIST_PREFIX;
import static com.ureca.ufit.global.auth.util.JwtUtil.REFRESH_TOKEN_EXPIRED_MS;

import com.ureca.ufit.entity.RefreshToken;
import com.ureca.ufit.global.auth.repository.RefreshTokenRepository;
import com.ureca.ufit.global.auth.util.JwtUtil;
import com.ureca.ufit.global.exception.CommonErrorCode;
import com.ureca.ufit.global.exception.RestApiException;
import jakarta.servlet.http.HttpServletResponse;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final SecretKey secretKey;
    private final RedisTemplate<String, String> redisTemplate;

    public void reissueToken(String bearerToken, String refreshToken, HttpServletResponse response) {

        // 어세스 토큰 추출
        String accessToken = bearerToken.substring(BEARER_PREFIX.length());

        // 블랙 리스트에 어세스 토큰이 있는 지 확인
        if(redisTemplate.hasKey(BLACKLIST_PREFIX + accessToken)){
            throw new RestApiException(CommonErrorCode.INVALID_TOKEN);
        }

        // 리프레시 토큰이 레디스에 있는지 확인
        RefreshToken refreshTokenEntity = refreshTokenRepository.findById(refreshToken).orElseThrow(()->
                new RestApiException(CommonErrorCode.REFRESH_NOT_FOUND)
        );

        // 어세스 토큰이 만료되 었는지 검증하고 이메일 추출
        String email = JwtUtil.getEmailOnlyIfExpired(accessToken, secretKey);

        // 어세스 토큰과 리프레시 토큰의 매핑 검증
        if (!email.equals(refreshTokenEntity.getEmail())) {
            throw new RestApiException(CommonErrorCode.REFRESH_DENIED);
        }

        // 리프레시 토큰 만료 여부 검증
        JwtUtil.validateRefreshToken(refreshToken,secretKey);

        // 리프레시 토큰 폐기 후 어세스 토큰과 리프레시 토큰 재발급 (RTR)
        String newRefreshToken = JwtUtil.createRefreshToken(email, secretKey);
        RefreshToken newRefreshTokenEntity = RefreshToken.of(newRefreshToken, email);
        refreshTokenRepository.deleteById(refreshToken);
        refreshTokenRepository.save(newRefreshTokenEntity);
        JwtUtil.updateRefreshTokenCookie(response, newRefreshToken,REFRESH_TOKEN_EXPIRED_MS/1000);

        String newAccessToken = JwtUtil.createAccessToken(email, secretKey);
        response.setHeader(AUTH_HEADER, BEARER_PREFIX + newAccessToken);
    }

}