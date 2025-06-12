package com.ureca.ufit.common.fixture;

import static lombok.AccessLevel.*;

import java.util.Map;

import com.ureca.ufit.entity.ChatBotReview;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class ChatBotReviewFixture {

	public static ChatBotReview chatBotReview(String content, String questionSummary) {
		return ChatBotReview.of(
			content,
			1,
			Map.of(),
			questionSummary
		);
	}
}
