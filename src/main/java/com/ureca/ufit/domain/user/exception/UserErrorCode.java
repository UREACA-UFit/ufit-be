package com.ureca.ufit.domain.user.exception;

import org.springframework.http.HttpStatus;

import com.ureca.ufit.global.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
	;

	private final HttpStatus httpStatus;
	private final String message;
}
