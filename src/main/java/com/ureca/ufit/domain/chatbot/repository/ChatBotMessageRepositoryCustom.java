package com.ureca.ufit.domain.chatbot.repository;

import org.springframework.data.domain.Pageable;

import com.ureca.ufit.domain.chatbot.dto.response.ChatMessageDto;
import com.ureca.ufit.entity.ChatRoom;
import com.ureca.ufit.global.dto.CursorPageResponse;

public interface ChatBotMessageRepositoryCustom {
	public CursorPageResponse<ChatMessageDto> findMessagesPage(ChatRoom chatRoom, Pageable pageable,
		String lastMessageId);
}
