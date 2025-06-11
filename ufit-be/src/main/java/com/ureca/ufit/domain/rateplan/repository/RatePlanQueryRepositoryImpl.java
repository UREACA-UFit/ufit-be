package com.ureca.ufit.domain.rateplan.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.ureca.ufit.domain.admin.dto.response.AdminRatePlanResponse;
import com.ureca.ufit.global.dto.CursorPageResponse;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RatePlanQueryRepositoryImpl implements RatePlanQueryRepository {

	private static final String IS_DELETED = "is_deleted";
	private static final String CURSOR = "_id";
	private static final String LOWEST_PRICE = "lowestPrice";
	private static final String HIGHEST_PRICE = "highestPrice";
	private static final String RATE_PLANS = "rate_plans";
	private static final String MONTHLY_FEE = "monthly_fee";

	private final MongoTemplate mongoTemplate;

	public CursorPageResponse<AdminRatePlanResponse> getRatePlansByCursor(
		String cursor, int size, String type
	) {
		Criteria criteria = Criteria.where(IS_DELETED).is(false);
		if (cursor != null && !cursor.isBlank()) {
			criteria.and(CURSOR).lt(cursor);
		}

		Sort.Order primary = getPrimaryOrder(type);
		Sort.Order secondary = Sort.Order.desc(CURSOR);

		List<AggregationOperation> pipeline = new ArrayList<>();
		pipeline.add(Aggregation.match(criteria));

		if (type != null && (LOWEST_PRICE.equalsIgnoreCase(type) || HIGHEST_PRICE.equalsIgnoreCase(type))) {
			pipeline.add(Aggregation.sort(Sort.by(primary, secondary)));
		} else {
			pipeline.add(Aggregation.sort(Sort.by(primary)));
		}
		pipeline.add(Aggregation.limit(size + 1));

		AggregationOperation project = Aggregation.project()
			.and("_id").as("ratePlanId")
			.and("plan_name").as("planName")
			.and("summary").as("summary")
			.and("monthly_fee").as("monthlyFee")
			.and("discount_fee").as("discountFee")
			.and("voice_allowance").as("voiceAllowance")
			.and("sms_allowance").as("smsAllowance")
			.and("basic_benefit").as("basicBenefit")
			.and("special_benefit").as("specialBenefit")
			.and("discount_benefit").as("discountBenefit")
			.and("createdAt").as("createdAt");

		pipeline.add(project);

		List<AdminRatePlanResponse> items = mongoTemplate.aggregate(
			Aggregation.newAggregation(pipeline),
			RATE_PLANS,
			AdminRatePlanResponse.class
		).getMappedResults();

		if (Objects.isNull(items) || items.isEmpty()) {
			return new CursorPageResponse<>(items, null, false);
		}

		boolean hasNext = items.size() > size;

		items = subLastPage(items, hasNext, size);

		String nextCursor = getNextCursor(items, hasNext);

		return new CursorPageResponse<>(items, nextCursor, hasNext);
	}

	private List<AdminRatePlanResponse> subLastPage(List<AdminRatePlanResponse> items, boolean hasNext, int size) {
		if (hasNext) {
			return items.subList(0, size);
		}

		return items;
	}

	private String getNextCursor(List<AdminRatePlanResponse> items, boolean hasNext) {
		if (!hasNext || Objects.isNull(items) || items.isEmpty()) {
			return null;
		}

		return items.get(items.size() - 1).ratePlanId().toString();
	}

	private Sort.Order getPrimaryOrder(String type) {
		if (LOWEST_PRICE.equals(type)) {
			return Sort.Order.asc(MONTHLY_FEE);
		} else if (HIGHEST_PRICE.equals(type)) {
			return Sort.Order.desc(MONTHLY_FEE);
		}
		return Sort.Order.desc(CURSOR);
	}
}
