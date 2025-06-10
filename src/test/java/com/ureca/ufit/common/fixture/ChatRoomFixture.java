package com.ureca.ufit.common.fixture;

import org.springframework.test.util.ReflectionTestUtils;

import com.ureca.ufit.entity.ChatRoom;
import com.ureca.ufit.entity.User;

public class ChatRoomFixture {
	public static ChatRoom chatRoom(Long id, User user) {
		ChatRoom chatRoom = ChatRoom.of(user);
		ReflectionTestUtils.setField(chatRoom, "id", id);
		return chatRoom;
	}
}
