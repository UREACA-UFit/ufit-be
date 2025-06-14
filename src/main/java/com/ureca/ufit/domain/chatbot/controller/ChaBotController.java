package com.ureca.ufit.domain.chatbot.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ureca.ufit.domain.chatbot.dto.request.CreateChatBotMessageRequest;
import com.ureca.ufit.domain.chatbot.dto.request.CreateChatBotReviewRequest;
import com.ureca.ufit.domain.chatbot.dto.response.ChatMessageDto;
import com.ureca.ufit.domain.chatbot.dto.response.ChatRoomCreateResponse;
import com.ureca.ufit.domain.chatbot.dto.response.CreateChatBotMessageResponse;
import com.ureca.ufit.domain.chatbot.dto.response.CreateChatBotReviewResponse;
import com.ureca.ufit.domain.chatbot.service.ChatBotMessageService;
import com.ureca.ufit.domain.chatbot.service.ChatBotReviewService;
import com.ureca.ufit.domain.chatbot.service.ChatRoomService;
import com.ureca.ufit.global.auth.details.CustomUserDetails;
import com.ureca.ufit.global.dto.CursorPageResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChaBotController implements ChaBotControllerApiSpec {

	private final ChatBotMessageService chatBotMessageService;
	private final ChatRoomService chatRoomService;
	private final ChatBotReviewService chatBotReviewService;

	@Override
	public ResponseEntity<CursorPageResponse<ChatMessageDto>> getMessages(Long chatRoomId, String lastMessageId,
		Pageable pageable) {
		CursorPageResponse<ChatMessageDto> response = chatBotMessageService.getChatMessages(chatRoomId, pageable,
			lastMessageId);
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<ChatRoomCreateResponse> getOrCreateChatRoom(CustomUserDetails userDetails) {
		ChatRoomCreateResponse response = chatRoomService.getOrCreateChatRoom(userDetails.email());
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<CreateChatBotReviewResponse> createChatBotReview(CreateChatBotReviewRequest request) {
		CreateChatBotReviewResponse response = chatBotReviewService.createChatBotReview(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Override
	public ResponseEntity<CreateChatBotMessageResponse> createChatBotMessage(CustomUserDetails userDetails,
		CreateChatBotMessageRequest request) {
		CreateChatBotMessageResponse response = chatBotMessageService.createChatBotMessage(request, userDetails.userId());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
