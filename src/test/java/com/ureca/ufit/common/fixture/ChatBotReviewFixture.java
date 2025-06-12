package com.ureca.ufit.common.fixture;

import com.ureca.ufit.entity.ChatBotReview;
import lombok.NoArgsConstructor;

import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ChatBotReviewFixture {

	public static ChatBotReview chatBotReview(int rating, Map<String, Object> recommendPlan) {
		return ChatBotReview.of(
			"추천 퀄리티가 너무 좋아서…",
			rating,
			recommendPlan,
			"넉넉한 데이터로 마음놓고 사용가능한 요금제");
	}



	public static ChatBotReview chatBotReview(String content, String questionSummary) {
		return ChatBotReview.of(
			content,
			1,
			Map.of(),
			questionSummary
		);
	}
}
