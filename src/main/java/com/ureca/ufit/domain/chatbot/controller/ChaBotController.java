package com.ureca.ufit.domain.chatbot.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.ureca.ufit.global.dto.CursorPageResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class ChaBotController {

	private final ChatBotMessageService chatBotMessageService;
	private final ChatRoomService chatRoomService;
	private final ChatBotReviewService chatBotReviewService;

	@GetMapping("/{chatroomId}")
	public ResponseEntity<CursorPageResponse<ChatMessageDto>> getMessages(
		@PathVariable("chatroomId") Long chatRoomId,
		@RequestParam(value = "lastMessageId", required = false) String lastMessageId,
		@PageableDefault(size = 10) Pageable pageable) {

		CursorPageResponse<ChatMessageDto> response = chatBotMessageService.getChatMessages(chatRoomId, pageable,
			lastMessageId);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/rooms")
	public ResponseEntity<ChatRoomCreateResponse> getOrCreateChatRoom(@AuthenticationPrincipal String email) {
		ChatRoomCreateResponse response = chatRoomService.getOrCreateChatRoom(email);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/review")
	public ResponseEntity<CreateChatBotReviewResponse> createChatBotReview(
		@RequestBody @Valid CreateChatBotReviewRequest request) {
		CreateChatBotReviewResponse response = chatBotReviewService.createChatBotReview(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/message")
	public ResponseEntity<CreateChatBotMessageResponse> createChatBotMessage(
		@AuthenticationPrincipal Long userId,
		@RequestBody @Valid CreateChatBotMessageRequest request) {
		CreateChatBotMessageResponse response = chatBotMessageService.createChatBotMessage(request, userId);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
