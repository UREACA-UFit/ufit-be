package com.ureca.ufit.domain.chatbot.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateChatBotMessageRequest(

	@NotNull
	@Size(min = 1, max = 300)
	@Schema(description = "사용자 질의 내용", example = "7살 아이가 사용할 요금제 추천해줘.")
	String content,

	@NotNull
	@Positive
	@Schema(description = "채팅방 ID", example = "10")
	Long chatRoomId

) {
}
