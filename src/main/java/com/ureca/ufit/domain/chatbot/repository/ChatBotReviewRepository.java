package com.ureca.ufit.domain.chatbot.repository;

import com.ureca.ufit.entity.ChatBotReview;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatBotReviewRepository extends MongoRepository<ChatBotReview, String>, ChatBotReviewQueryRepository {
}