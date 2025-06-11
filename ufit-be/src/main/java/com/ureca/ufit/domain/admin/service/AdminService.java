package com.ureca.ufit.domain.admin.service;

import org.springframework.stereotype.Service;

import com.ureca.ufit.domain.admin.dto.response.AdminRatePlanResponse;
import com.ureca.ufit.domain.rateplan.repository.RatePlanRepository;
import com.ureca.ufit.global.dto.CursorPageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final RatePlanRepository ratePlanRepository;

	public CursorPageResponse<AdminRatePlanResponse> getRatePlansByCursor(String cursor, int size, String type) {
		return ratePlanRepository.getRatePlansByCursor(cursor, size, type);
	}
}
