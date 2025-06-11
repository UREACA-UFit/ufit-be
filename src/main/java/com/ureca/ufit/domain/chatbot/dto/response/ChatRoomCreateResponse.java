package com.ureca.ufit.domain.chatbot.dto.response;

public record ChatRoomCreateResponse(
	Long chatRoomId,
	boolean isAnonymous
) {
}
