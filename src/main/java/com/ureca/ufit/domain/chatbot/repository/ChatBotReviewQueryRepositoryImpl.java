package com.ureca.ufit.domain.chatbot.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ureca.ufit.domain.admin.dto.response.ChatBotReviewResponse;
import com.ureca.ufit.entity.ChatBotReview;
import com.ureca.ufit.global.dto.CursorPageResponse;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatBotReviewQueryRepositoryImpl implements ChatBotReviewQueryRepository {

	private static final String COLLECTION = "chat_bot_reviews";
	private static final String CREATED_AT = "createdAt";
	private final MongoTemplate mongoTemplate;

	public CursorPageResponse<ChatBotReviewResponse> getChatBotReviewByCursor(
		String cursor, int size) {

		// 1) cursor(null)이면 전체, 아니면 _id < cursor 조건
		Criteria criteria = new Criteria();
		if (cursor != null && !cursor.isBlank()) {
			criteria = Criteria.where("_id").lt(new ObjectId(cursor));
		}

		// 2) DESC 정렬(_id 기준) + size+1 로 조회
		Query query = new Query(criteria)
			.with(Sort.by(Sort.Direction.DESC, "_id"))
			.limit(size + 1);

		// 3) 실제 도큐먼트 조회
		List<ChatBotReview> docs = mongoTemplate.find(query, ChatBotReview.class);

		// 4) hasNext, 실제 items 자르기
		boolean hasNext = docs.size() > size;
		List<ChatBotReviewResponse> items = docs.stream()
			.limit(size)
			.map(e -> new ChatBotReviewResponse(
				e.getId(),                           // chatBotReviewId
				e.getQuestionSummary(),              // questionSummery
				e.getRecommendPlan().toString(),     // recommendPlan (String 타입)
				e.getRating(),                       // rate
				e.getContent()
			))
			.toList();

		// 5) nextCursor는 마지막 아이템의 id
		String nextCursor = hasNext
			? items.get(items.size() - 1).chatBotReviewId()
			: null;

		return new CursorPageResponse<>(items, nextCursor, hasNext);
	}

}
