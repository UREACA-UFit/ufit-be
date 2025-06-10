package com.ureca.ufit.entity;

import static lombok.AccessLevel.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.ureca.ufit.global.domain.MongoTimeBaseEntity;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "chat_bot_messages")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ChatBotMessage extends MongoTimeBaseEntity {

	@Id
	@Field("chat_bot_message_id")
	private String id;

	@NotNull
	@Field("content")
	private String content;

	@NotNull
	@Field("owner")
	private boolean owner;

	@NotNull
	@Field("userId")
	private Long userId;

	@NotNull
	@Field("a_plan_id")
	private Long aPlanId;

	@NotNull
	@Field("b_plan_id")
	private Long bPlanId;

	@NotNull
	@Field("chat_room_id")
	private Long chatRoomId;

	@Builder(access = PRIVATE)
	private ChatBotMessage(String content, boolean owner, Long userId, Long aPlanId, Long bPlanId, Long chatRoomId) {
		this.content = content;
		this.owner = owner;
		this.userId = userId;
		this.aPlanId = aPlanId;
		this.bPlanId = bPlanId;
		this.chatRoomId = chatRoomId;
	}
}
