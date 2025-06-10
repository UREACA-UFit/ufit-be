package com.ureca.ufit.domain.admin.service;

import org.springframework.stereotype.Service;

import com.ureca.ufit.domain.admin.dto.response.AdminRatePlanResponse;
import com.ureca.ufit.domain.ratePlan.repository.RatePlanQueryRepository;
import com.ureca.ufit.global.dto.CursorPageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final RatePlanQueryRepository ratePlanQueryRepository;

	public CursorPageResponse<AdminRatePlanResponse> getRatePlansByCursor(String cursor, int size, String type) {
		return ratePlanQueryRepository.getRatePlansByCursor(cursor, size, type);
	}
}
