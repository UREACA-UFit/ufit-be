package com.ureca.ufit.entity;

import static lombok.AccessLevel.*;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.ureca.ufit.global.domain.MongoTimeBaseEntity;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "chat_bot_review")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ChatBotReview extends MongoTimeBaseEntity {

	@Id
	private String id;

	@NotNull
	@Field("content")
	private String content;

	@NotNull
	@Field("rating")
	private int rating;

	@NotNull
	@Field("recommend_plan")
	private Map<String, Object> recommendPlan;

	@NotNull
	@Field("question_summary")
	private String questionSummary;

	@Builder(access = PRIVATE)
	private ChatBotReview(String content, int rating, Map<String, Object> recommendPlan, String questionSummary) {
		this.content = content;
		this.rating = rating;
		this.recommendPlan = recommendPlan;
		this.questionSummary = questionSummary;
	}
}
