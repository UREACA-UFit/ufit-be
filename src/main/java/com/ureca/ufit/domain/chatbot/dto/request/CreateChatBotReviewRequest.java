package com.ureca.ufit.domain.chatbot.dto.request;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateChatBotReviewRequest(

	@NotNull
	@Positive
	@Max(5)
	@Schema(description = "별점", example = "3")
	Integer rating,

	@Schema(description = "리뷰 내용", example = "추천 받은 요금제가 딱 찾던거에요.")
	String content,

	@NotEmpty
	@Schema(
		description = "추천된 요금제 집합",
		type = "object",
		example = """
			{
			  "aPlan": "5G 무제한",
			  "bPlan": "LTE 자유 11GB"
			}
			"""
	)
	Map<String, Object> recommendPlans,

	@NotNull
	@Positive
	@Schema(description = "성별", example = "10")
	Long chatRoomId
) {
}
