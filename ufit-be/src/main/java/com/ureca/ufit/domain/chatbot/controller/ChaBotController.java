package com.ureca.ufit.domain.chatbot.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ureca.ufit.domain.chatbot.dto.response.ChatMessageDto;
import com.ureca.ufit.domain.chatbot.service.ChatBotMessageService;
import com.ureca.ufit.global.dto.CursorPageResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class ChaBotController {

	private final ChatBotMessageService chatBotMessageService;

	@GetMapping("/{chatroomId}")
	public ResponseEntity<CursorPageResponse<ChatMessageDto>> getMessages(
		@PathVariable("chatroomId") Long chatRoomId,
		@RequestParam(value = "lastMessageId", required = false) String lastMessageId,
		@PageableDefault(size = 10) Pageable pageable) {

		CursorPageResponse<ChatMessageDto> response = chatBotMessageService.getChatMessages(chatRoomId, pageable,
			lastMessageId);
		return ResponseEntity.ok(response);
	}
}
