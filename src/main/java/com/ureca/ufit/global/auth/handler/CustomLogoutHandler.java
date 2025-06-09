package com.ureca.ufit.global.auth.handler;

import static com.ureca.ufit.global.auth.util.JwtUtil.AUTH_HEADER;
import static com.ureca.ufit.global.auth.util.JwtUtil.BEARER_PREFIX;
import static com.ureca.ufit.global.auth.util.JwtUtil.BLACKLIST_PREFIX;

import com.ureca.ufit.global.auth.repository.RefreshTokenRepository;
import com.ureca.ufit.global.auth.util.JwtUtil;
import com.ureca.ufit.global.exception.CommonErrorCode;
import com.ureca.ufit.global.exception.RestApiException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final RefreshTokenRepository refreshTokenRepository;
    private final RedisTemplate<String,String> redisTemplate;
    private final SecretKey secretKey;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {

        // 로그아웃 시 헤더에 있는 어세스 토큰 검증
        String bearerToken = request.getHeader(AUTH_HEADER);
        if( bearerToken == null || !bearerToken.startsWith(BEARER_PREFIX)){
            throw new RestApiException(CommonErrorCode.NOT_EXIST_BEARER_SUFFIX);
        }
        String accessToken = bearerToken.substring(7);
        JwtUtil.validateAccessToken(accessToken, secretKey);

        // 블랙 리스트에 어세스 토큰 추가
        addToBlacklistRedis(accessToken);

        // 레디스에서 리프레시 토큰 삭제
        String refreshToken = JwtUtil.getRefreshTokenCookies(request);
        if (refreshToken != null) {
            // Redis에서 해당 리프레시 토큰 키 삭제
            refreshTokenRepository.findById(refreshToken)
                    .ifPresent(refreshTokenRepository::delete);
        }

        // 쿠키에서 리프레시 토큰 삭제 (timeout을 0으로 두어 즉시 삭제)
        JwtUtil.setRefreshTokenCookie(response, null, 0);
    }

    private void addToBlacklistRedis(String accessToken) {
        Date expiration = JwtUtil.getExpiration(accessToken, secretKey);
        long ttl = expiration.getTime() - System.currentTimeMillis(); // TTL: 남은 시간 - 현재 시간
        redisTemplate.opsForValue().set(BLACKLIST_PREFIX + accessToken, "logout", ttl, TimeUnit.MILLISECONDS);
    }
}
