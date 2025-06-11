package com.ureca.ufit.domain.chatbot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ureca.ufit.domain.chatbot.dto.ChatRoomMapper;
import com.ureca.ufit.domain.chatbot.dto.response.ChatRoomCreateResponse;
import com.ureca.ufit.domain.chatbot.repository.ChatRoomRepository;
import com.ureca.ufit.domain.user.repository.UserRepository;
import com.ureca.ufit.entity.ChatRoom;
import com.ureca.ufit.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

	private static final String ANONYMOUS_USER = "anonymousUser";

	private final ChatRoomRepository chatRoomRepository;
	private final UserRepository userRepository;

	@Transactional
	public ChatRoomCreateResponse getOrCreateChatRoom(String email) {
		if (ANONYMOUS_USER.equals(email)) {
			ChatRoom newChatRoom = chatRoomRepository.save(ChatRoom.of(null));
			return ChatRoomMapper.toChatroomCreateResponse(newChatRoom);
		}

		User findUser = userRepository.getByEmail(email);
		return chatRoomRepository.findByUser(findUser)
			.map(ChatRoomMapper::toChatroomCreateResponse)
			.orElseGet(() -> {
				ChatRoom savedChatRoom = chatRoomRepository.save(ChatRoom.of(findUser));
				return ChatRoomMapper.toChatroomCreateResponse(savedChatRoom);
			});
	}
}
