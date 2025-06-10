package com.ureca.ufit.domain.ratePlan.service;

import com.ureca.ufit.domain.ratePlan.dto.response.RatePlanDetailResponse;
import com.ureca.ufit.domain.ratePlan.dto.response.RatePlanListResponse;
import com.ureca.ufit.entity.RatePlan;
import com.ureca.ufit.domain.ratePlan.repository.RatePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatePlanService {

    private final RatePlanRepository ratePlanRepository;

    /**
     * 페이징 처리된 요금제 목록 조회
     */
    public Page<RatePlanListResponse> getRatePlanList(Pageable pageable) {
        return ratePlanRepository
                .findByEnabledTrueAndDeletedFalse(pageable)
                .map(RatePlanListResponse::from);
    }

    /**
     * 필터링된 전체 요금제 목록 조회
     */
    public List<RatePlanListResponse> getRatePlanList() {
        return ratePlanRepository.findAll().stream()
                .filter(RatePlan::isEnabled)
                .filter(plan -> !plan.isDeleted())
                .map(RatePlanListResponse::from)
                .toList();
    }

    /**
     * 단일 요금제 상세 조회
     */
    public RatePlanDetailResponse getRatePlanDetail(String id) {
        RatePlan ratePlan = ratePlanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 요금제가 존재하지 않습니다: " + id));
        return RatePlanDetailResponse.from(ratePlan);
    }
}