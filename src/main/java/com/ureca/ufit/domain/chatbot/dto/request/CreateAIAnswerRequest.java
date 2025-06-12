package com.ureca.ufit.domain.chatbot.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateAIAnswerRequest(

	@Positive
	Long userId,

	@NotNull
	String content,

	@NotNull
	@Positive
	Long chatRoomId
) {
}
