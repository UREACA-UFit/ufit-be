package com.ureca.ufit.domain.chatbot.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import com.ureca.ufit.domain.chatbot.dto.response.ChatMessageDto;
import com.ureca.ufit.entity.ChatRoom;
import com.ureca.ufit.global.dto.CursorPageResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChatBotMessageRepositoryImpl implements ChatBotMessageRepositoryCustom {

	private static final String COLLECTION = "chat_bot_messages";
	private static final String FIELD_ID = "_id";
	private static final String FIELD_CHAT_ROOM_ID = "chat_room_id";

	private final MongoTemplate mongoTemplate;

	@Override
	public CursorPageResponse<ChatMessageDto> findMessagesPage(ChatRoom chatRoom, Pageable pageable,
		String lastMessageId) {
		List<AggregationOperation> pipeline = new ArrayList<>();

		Criteria criteria = Criteria.where(FIELD_CHAT_ROOM_ID).is(chatRoom.getId());
		if (lastMessageId != null && !lastMessageId.isBlank()) {
			criteria = criteria.and(FIELD_ID).lt(lastMessageId);
		}
		MatchOperation match = Aggregation.match(criteria);
		pipeline.add(match);

		Sort.Order primary = Sort.Order.desc(FIELD_ID);
		pipeline.add(Aggregation.sort(Sort.by(primary)));
		pipeline.add(Aggregation.limit(pageable.getPageSize() + 1));

		ProjectionOperation project = Aggregation.project()
			.and("_id").as("messageId")
			.and("content").as("content")
			.and("owner").as("owner")
			.and("a_plan_id").as("aPlanId")
			.and("b_plan_id").as("bPlanId");
		pipeline.add(project);

		Aggregation aggregation = Aggregation.newAggregation(pipeline);
		AggregationResults<ChatMessageDto> results = mongoTemplate.aggregate(
			aggregation,
			COLLECTION,
			ChatMessageDto.class
		);

		List<ChatMessageDto> messages = results.getMappedResults();
		boolean hasNext = messages.size() > pageable.getPageSize();
		if (hasNext) {
			messages = messages.subList(0, pageable.getPageSize());
		}

		String nextCursor = hasNext ? messages.get(messages.size() - 1).messageId() : null;

		return new CursorPageResponse<>(messages, nextCursor, hasNext);
	}
}
