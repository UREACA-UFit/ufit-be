package com.ureca.ufit.domain.chatbot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ureca.ufit.domain.chatbot.exception.ChatBotErrorCode;
import com.ureca.ufit.entity.ChatRoom;
import com.ureca.ufit.entity.User;
import com.ureca.ufit.global.exception.RestApiException;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

	Optional<ChatRoom> findByUser(User user);

	default ChatRoom getById(Long id) {
		return findById(id).orElseThrow(() -> new RestApiException(ChatBotErrorCode.CHATROOM_NOT_FOUND));
	}
}
