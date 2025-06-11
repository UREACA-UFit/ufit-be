package com.ureca.ufit.domain.admin.service;

import com.ureca.ufit.domain.admin.dto.ChatBotReviewMapper;
import com.ureca.ufit.domain.admin.dto.RatePlanMapper;
import com.ureca.ufit.domain.admin.dto.request.CreateRatePlanRequest;
import com.ureca.ufit.domain.admin.dto.response.*;
import com.ureca.ufit.domain.rateplan.exception.RatePlanErrorCode;
import com.ureca.ufit.domain.rateplan.repository.RatePlanRepository;
import com.ureca.ufit.entity.RatePlan;
import com.ureca.ufit.global.dto.CursorPageResponse;
import com.ureca.ufit.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final RatePlanRepository ratePlanRepository;

	public CursorPageResponse<AdminRatePlanResponse> getRatePlansByCursor(String cursor, int size, String type) {
		return ratePlanRepository.getRatePlansByCursor(cursor, size, type);
	}


	public CreateRatePlanResponse createRatePlan(CreateRatePlanRequest createRatePlanRequest) {
		RatePlan savedRatePlan = ratePlanRepository.save(RatePlanMapper.toEntity(createRatePlanRequest));
		return RatePlanMapper.toCreateRateResponse();
	}

	public DeleteRatePlanResponse deleteRatePlan(String ratePlanId) {
		RatePlan ratePlan = ratePlanRepository.findById(ratePlanId)
				.orElseThrow(()-> new RestApiException(RatePlanErrorCode.RATEPLAN_NOT_FOUND)
		);
		ratePlan.updateDeleteStatus();
		ratePlanRepository.save(ratePlan);

		return RatePlanMapper.toDeleteRateResponse();
	}

	public RatePlanMetricsResponse getRatePlanMetrics() {
		return RatePlanMapper.toRatePlanMetricsResponse();
	}

	public ChatBotReviewResponse getChatBotReview(){
		return ChatBotReviewMapper.toChatBotReview();
	}

}
