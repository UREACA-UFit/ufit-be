package com.ureca.ufit.domain.admin.controller;

import com.ureca.ufit.domain.admin.dto.request.CreateRatePlanRequest;
import com.ureca.ufit.domain.admin.dto.response.*;
import com.ureca.ufit.domain.admin.service.AdminService;
import com.ureca.ufit.global.dto.CursorPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;

	@GetMapping("/api/admin/rateplans")
	public ResponseEntity<CursorPageResponse<AdminRatePlanResponse>> getRatePlansByCursor(
		@RequestParam(name = "cursor", required = false) String cursor,
		@RequestParam(name = "size", defaultValue = "20") int size,
		@RequestParam(name = "type", required = false) String type
	) {
		CursorPageResponse<AdminRatePlanResponse> response = adminService.getRatePlansByCursor(cursor, size, type);
		return ResponseEntity.ok(response);
	}

    // 요금제 생성
    @PostMapping("/api/admin/rateplans")
    public ResponseEntity<CreateRatePlanResponse> createRatePlan(@RequestBody CreateRatePlanRequest createRatePlanRequest) {
        CreateRatePlanResponse response = adminService.createRatePlan(createRatePlanRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 요금제 삭제
    @PostMapping("/api/admin/rateplans/{ratePlanId}")
    public ResponseEntity<DeleteRatePlanResponse> deleteRatePlan(@PathVariable String ratePlanId) {
        DeleteRatePlanResponse response = adminService.deleteRatePlan(ratePlanId);
        return ResponseEntity.ok(response);
    }

    // 요금제 지표 조회
//    @GetMapping("/api/admin/rateplans/metrics")
//    public ResponseEntity<RatePlanMetricsResponse> getRatePlanMetrics(){
//        ResponseEntity response = adminService.getRatePlanMetrics();
//        return ResponseEntity.ok();
//    }

    // 챗봇 리뷰 조회
    @GetMapping("/api/admin/chats/reviews")
    public ResponseEntity<CursorPageResponse<ChatBotReviewResponse>> getChatBotReviewByCursor(
        @RequestParam(name = "cursor", required = false) String cursor,
        @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        CursorPageResponse <ChatBotReviewResponse> response = adminService.getChatBotReview(cursor, size);
        return ResponseEntity.ok(response);
    }

}