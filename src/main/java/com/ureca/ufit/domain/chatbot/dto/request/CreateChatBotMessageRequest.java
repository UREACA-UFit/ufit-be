package com.ureca.ufit.domain.chatbot.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateChatBotMessageRequest(

	@NotNull
	@Size(min = 1, max = 300)
	String content,

	@NotNull
	@Positive
	Long chatRoomId

) {
}
