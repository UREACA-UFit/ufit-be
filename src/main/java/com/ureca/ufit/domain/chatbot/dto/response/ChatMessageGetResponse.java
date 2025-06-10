package com.ureca.ufit.domain.chatbot.dto.response;

import java.util.List;

public record ChatMessageGetResponse(
	List<ChatMessageDto> messages,
	Boolean hasNext
) {
}
