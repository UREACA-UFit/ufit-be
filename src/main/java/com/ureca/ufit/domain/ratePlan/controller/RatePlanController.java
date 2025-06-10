package com.ureca.ufit.domain.ratePlan.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.ureca.ufit.domain.ratePlan.dto.response.RatePlanDetailResponse;
import com.ureca.ufit.domain.ratePlan.dto.response.RatePlanListResponse;
import com.ureca.ufit.domain.ratePlan.service.RatePlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rateplans")
@RequiredArgsConstructor
public class RatePlanController {

    private final RatePlanService ratePlanService;

    // 전체 요금제 목록 조회 (페이징)
    @GetMapping
    public ResponseEntity<Page<RatePlanListResponse>> getRatePlans(Pageable pageable) {
        Page<RatePlanListResponse> page = ratePlanService.getRatePlanList(pageable);
        return ResponseEntity.ok(page);
    }

    // 특정 요금제 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<RatePlanDetailResponse> getRatePlan(@PathVariable String id) {
        return ResponseEntity.ok(ratePlanService.getRatePlanDetail(id));
    }
}
