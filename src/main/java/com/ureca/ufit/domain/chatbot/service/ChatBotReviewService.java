package com.ureca.ufit.domain.chatbot.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ureca.ufit.domain.admin.dto.ChatBotReviewMapper;
import com.ureca.ufit.domain.chatbot.dto.request.CreateChatBotReviewRequest;
import com.ureca.ufit.domain.chatbot.dto.response.CreateChatBotReviewResponse;
import com.ureca.ufit.domain.chatbot.dto.response.QuestionSummaryDto;
import com.ureca.ufit.domain.chatbot.repository.ChatBotReviewRepository;
import com.ureca.ufit.entity.ChatBotReview;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatBotReviewService {

	private final RestTemplate restTemplate;
	private final ChatBotReviewRepository chatBotReviewRepository;

	public CreateChatBotReviewResponse createChatBotReview(CreateChatBotReviewRequest request) {
		// TODO : Fast API 리뷰 요약 요청

		final String url = "";
		QuestionSummaryDto questionSummaryDto = restTemplate.getForObject(url, QuestionSummaryDto.class);

		ChatBotReview chatBotReview = ChatBotReviewMapper.toChatBotReview(request, questionSummaryDto);

		chatBotReviewRepository.save(chatBotReview);

		return ChatBotReviewMapper.toCreateChatBotReviewResponse();
	}
}
