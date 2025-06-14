package com.ureca.ufit.domain.rateplan.repository;

import com.ureca.ufit.domain.admin.dto.response.AdminRatePlanResponse;
import com.ureca.ufit.domain.rateplan.dto.response.RatePlanDetailResponse;
import com.ureca.ufit.domain.rateplan.dto.response.RatePlanPreviewResponse;
import com.ureca.ufit.global.dto.CursorPageResponse;
import com.ureca.ufit.entity.RatePlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RatePlanQueryRepository {

	public CursorPageResponse<AdminRatePlanResponse> getRatePlansByCursor(String cursor, int size, String type);

	Page<RatePlan> findEnabledRatePlansWithSort(Pageable pageable, String sortType);

	Page<RatePlanPreviewResponse> getRatePlanPreviews(Pageable pageable, String sortType);

	Optional<RatePlanDetailResponse> getRatePlanDetailById(String id);
}
