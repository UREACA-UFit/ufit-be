package com.ureca.ufit.domain.chatbot.dto;

import com.ureca.ufit.domain.chatbot.dto.request.CreateAIAnswerRequest;
import com.ureca.ufit.domain.chatbot.dto.request.CreateChatBotMessageRequest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessageMapper {
	public static CreateAIAnswerRequest toCreateAIAnswerRequest(CreateChatBotMessageRequest request, Long userId) {
		return new CreateAIAnswerRequest(userId, request.content(), request.chatRoomId());
	}

}
