package com.ureca.ufit.domain.chatbot.dto.request;

import java.util.Map;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateChatBotReviewRequest(

	@NotNull
	@Positive
	@Max(5)
	Integer rating,

	String content,

	@NotEmpty
	Map<String, Object> recommendPlans,

	@NotNull
	@Positive
	Long chatRoomId
) {
}
