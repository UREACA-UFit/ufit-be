package com.ureca.ufit.domain.chatbot.dto;

import com.ureca.ufit.domain.chatbot.dto.response.ChatRoomCreateResponse;
import com.ureca.ufit.entity.ChatRoom;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoomMapper {

	public static ChatRoomCreateResponse toChatroomCreateResponse(ChatRoom chatRoom) {
		boolean isAnonymous = (chatRoom.getUser() == null);
		return new ChatRoomCreateResponse(chatRoom.getId(), isAnonymous);
	}
}
