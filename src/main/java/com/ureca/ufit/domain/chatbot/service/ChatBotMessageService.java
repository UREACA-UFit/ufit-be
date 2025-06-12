package com.ureca.ufit.domain.chatbot.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ureca.ufit.domain.chatbot.dto.response.ChatMessageDto;
import com.ureca.ufit.domain.chatbot.repository.ChatBotMessageRepository;
import com.ureca.ufit.domain.chatbot.repository.ChatRoomRepository;
import com.ureca.ufit.entity.ChatRoom;
import com.ureca.ufit.global.dto.CursorPageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatBotMessageService {

	private final ChatBotMessageRepository chatBotMessageRepository;
	private final ChatRoomRepository chatRoomRepository;

	public CursorPageResponse<ChatMessageDto> getChatMessages(Long chatRoomId, Pageable pageable,
		String lastMessageId) {
		ChatRoom findChatRoom = chatRoomRepository.getById(chatRoomId);

		return chatBotMessageRepository.findMessagesPage(findChatRoom, pageable, lastMessageId);
	}
}
