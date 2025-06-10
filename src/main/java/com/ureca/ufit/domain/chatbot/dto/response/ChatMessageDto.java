package com.ureca.ufit.domain.chatbot.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ChatMessageDto(
	String messageId,
	String content,
	Boolean owner,
	Long aPlanId,
	Long bPlanId
) {
}
