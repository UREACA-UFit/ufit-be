package com.ureca.ufit.domain.admin.dto.response;

public record ChatBotReviewResponse(
	String chatBotReviewId,
	String questionSummary,
	String recommendPlan,
	int rate,
	String content
) {
}
