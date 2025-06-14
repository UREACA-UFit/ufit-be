package com.ureca.ufit.domain.chatbot.dto.response;

import java.util.Map;

import com.ureca.ufit.entity.enums.AnswerType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateChatBotMessageResponse(

	@NotNull
	@Positive
	Long messageId,

	@Positive
	Long userId,

	@NotNull
	String answer,

	@NotNull
	AnswerType answerType,

	Map<String, Map<String, Object>> recommendPlans
) {
}
