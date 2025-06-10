package com.ureca.ufit.domain.rateplan.repository;

import com.ureca.ufit.domain.admin.dto.response.AdminRatePlanResponse;
import com.ureca.ufit.global.dto.CursorPageResponse;

public interface RatePlanQueryRepository {

	public CursorPageResponse<AdminRatePlanResponse> getRatePlansByCursor(String cursor, int size, String type);
}
