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
     * 페이지네이션 + 정렬(type) 적용
     */
    public Page<RatePlanListResponse> getRatePlanList(Pageable pageable, String sortType) {
        return ratePlanRepository
                .findEnabledRatePlansWithSort(pageable, sortType)
                .map(RatePlanListResponse::from);
    }

    /**
     * 단일 요금제 상세 조회
     */
    public RatePlanDetailResponse getRatePlanDetail(String id) {
        RatePlan ratePlan = ratePlanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 요금제가 존재하지 않습니다: " + id));
        if (!ratePlan.isEnabled() || ratePlan.isDeleted()) {
            throw new IllegalArgumentException("조회할 수 없는 요금제입니다: " + id);
        }
        return RatePlanDetailResponse.from(ratePlan);
    }
}