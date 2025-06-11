package com.ureca.ufit.domain.ratePlan.repository;

import com.ureca.ufit.domain.admin.dto.response.AdminRatePlanResponse;
import com.ureca.ufit.global.dto.CursorPageResponse;
import com.ureca.ufit.entity.RatePlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RatePlanQueryRepository {

	public CursorPageResponse<AdminRatePlanResponse> getRatePlansByCursor(String cursor, int size, String type);

	// 사용자용 요금제 목록 조회
	public Page<RatePlan> findEnabledRatePlansWithSort(Pageable pageable, String sortType);
}
