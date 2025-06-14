package com.ureca.ufit.entity;

import static com.ureca.ufit.global.auth.util.JwtUtil.*;
import static lombok.AccessLevel.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import com.ureca.ufit.global.auth.util.JwtUtil;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@RedisHash(value = REFRESH_TOKEN_PREFIX, timeToLive = JwtUtil.REFRESH_TOKEN_EXPIRED_MS)
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
public class RefreshToken {

	@Id
	private String refreshToken;

	private String email;

	@Builder(access = PRIVATE)
	public RefreshToken(String refreshToken, String email) {
		this.refreshToken = refreshToken;
		this.email = email;
	}

	public static RefreshToken of(String refreshToken, String email) {
		return RefreshToken.builder()
			.refreshToken(refreshToken)
			.email(email)
			.build();
	}
}
