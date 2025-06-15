package com.ureca.ufit.domain.admin.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ureca.ufit.domain.admin.dto.RatePlanMapper;
import com.ureca.ufit.domain.admin.dto.request.CreateRatePlanRequest;
import com.ureca.ufit.domain.admin.dto.response.AdminRatePlanResponse;
import com.ureca.ufit.domain.admin.dto.response.ChatBotReviewResponse;
import com.ureca.ufit.domain.admin.dto.response.CreateRatePlanResponse;
import com.ureca.ufit.domain.admin.dto.response.DeleteRatePlanResponse;
import com.ureca.ufit.domain.admin.dto.response.RatePlanMetricsResponse;
import com.ureca.ufit.domain.chatbot.repository.ChatBotReviewRepository;
import com.ureca.ufit.domain.rateplan.exception.RatePlanErrorCode;
import com.ureca.ufit.domain.rateplan.repository.RatePlanRepository;
import com.ureca.ufit.domain.user.repository.UserRepository;
import com.ureca.ufit.entity.RatePlan;
import com.ureca.ufit.global.dto.CursorPageResponse;
import com.ureca.ufit.global.exception.RestApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final RatePlanRepository ratePlanRepository;
	private final ChatBotReviewRepository chatBotReviewRepository;
	private final UserRepository userRepository;

	public CursorPageResponse<AdminRatePlanResponse> getRatePlansByCursor(String cursor, int size, String type) {
		return ratePlanRepository.getRatePlansByCursor(cursor, size, type);
	}

	public CreateRatePlanResponse createRatePlan(CreateRatePlanRequest createRatePlanRequest) {
		RatePlan savedRatePlan = ratePlanRepository.save(RatePlanMapper.toEntity(createRatePlanRequest));
		return RatePlanMapper.toCreateRateResponse();
	}

	public DeleteRatePlanResponse deleteRatePlan(String ratePlanId) {
		RatePlan ratePlan = ratePlanRepository.findById(ratePlanId)
			.orElseThrow(() -> new RestApiException(RatePlanErrorCode.RATEPLAN_NOT_FOUND)
			);
		ratePlan.updateDeleteStatus();
		ratePlanRepository.save(ratePlan);

		return RatePlanMapper.toDeleteRateResponse();
	}

	public RatePlanMetricsResponse getRatePlanMetrics(int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size);
		Page<RatePlan> pageResult = ratePlanRepository.findEnabledRatePlansWithSort(pageable, "NAME_ASC");

		List<RatePlan> items = pageResult.getContent();
		long totalCount = pageResult.getTotalElements();

		Map<String, Long> subscriberMap = userRepository.countUsersByRatePlan().stream()
			.collect(Collectors.toMap(
				UserRepository.RatePlanCountProjection::getRatePlanId,
				UserRepository.RatePlanCountProjection::getCount
			));

		items.sort(Comparator.comparingLong(
			(RatePlan plan) -> subscriberMap.getOrDefault(plan.getId(), 0L)
		).reversed());


		return RatePlanMapper.toRatePlanMetricsResponse(items, subscriberMap, page, size, totalCount);
	}

	public CursorPageResponse<ChatBotReviewResponse> getChatBotReview(String cursor, int size) {
		return chatBotReviewRepository.getChatBotReviewByCursor(cursor, size);
	}

}
