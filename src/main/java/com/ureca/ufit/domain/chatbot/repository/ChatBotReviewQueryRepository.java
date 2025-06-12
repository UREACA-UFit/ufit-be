package com.ureca.ufit.domain.chatbot.repository;

import org.springframework.stereotype.Repository;

import com.ureca.ufit.domain.admin.dto.response.ChatBotReviewResponse;
import com.ureca.ufit.global.dto.CursorPageResponse;

@Repository
public interface ChatBotReviewQueryRepository {
	CursorPageResponse<ChatBotReviewResponse> getChatBotReviewByCursor(String cursor, int size);
}
