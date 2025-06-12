package com.ureca.ufit.domain.chatbot.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ureca.ufit.domain.chatbot.dto.ChatMessageMapper;
import com.ureca.ufit.domain.chatbot.dto.request.CreateAIAnswerRequest;
import com.ureca.ufit.domain.chatbot.dto.request.CreateChatBotMessageRequest;
import com.ureca.ufit.domain.chatbot.dto.response.ChatMessageDto;
import com.ureca.ufit.domain.chatbot.dto.response.CreateChatBotMessageResponse;
import com.ureca.ufit.domain.chatbot.exception.ChatBotErrorCode;
import com.ureca.ufit.domain.chatbot.repository.ChatBotMessageRepository;
import com.ureca.ufit.domain.chatbot.repository.ChatRoomRepository;
import com.ureca.ufit.entity.ChatRoom;
import com.ureca.ufit.global.dto.CursorPageResponse;
import com.ureca.ufit.global.exception.RestApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatBotMessageService {

	private final ChatBotMessageRepository chatBotMessageRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final RestTemplate restTemplate;
	private final String fastApiUrl = "https://ai.u-fit.site/api/chats/message/ai";

	public CursorPageResponse<ChatMessageDto> getChatMessages(Long chatRoomId, Pageable pageable,
		String lastMessageId) {
		ChatRoom findChatRoom = chatRoomRepository.getById(chatRoomId);

		return chatBotMessageRepository.findMessagesPage(findChatRoom, pageable, lastMessageId);
	}

	public CreateChatBotMessageResponse createChatBotMessage(CreateChatBotMessageRequest request, Long userId) {

		// Todo : content에 대한 금칙어 판단 필요

		CreateAIAnswerRequest createAIAnswerRequest = ChatMessageMapper.toCreateAIAnswerRequest(request, userId);

		try {
			return restTemplate.postForObject(
				fastApiUrl,
				createAIAnswerRequest,
				CreateChatBotMessageResponse.class
			);

		} catch (Exception e) {
			throw new RestApiException(ChatBotErrorCode.LLM_TIMEOUT);
		}
	}
}
