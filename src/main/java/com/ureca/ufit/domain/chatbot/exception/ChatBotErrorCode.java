package com.ureca.ufit.domain.chatbot.exception;

import org.springframework.http.HttpStatus;

import com.ureca.ufit.global.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChatBotErrorCode implements ErrorCode {

	CHATROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "채팅방을 찾을 수 없습니다."),
	;

	private final HttpStatus httpStatus;
	private final String message;
}
