package com.ureca.ufit.domain.chatbot.repository;

import com.ureca.ufit.domain.admin.dto.response.ChatBotReviewResponse;
import com.ureca.ufit.global.dto.CursorPageResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatBotReviewQueryRepository {
    CursorPageResponse<ChatBotReviewResponse> getChatBotReviewByCursor(String cursor, int size);
}
