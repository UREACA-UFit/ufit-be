package com.ureca.ufit.domain.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ureca.ufit.domain.admin.dto.response.AdminRatePlanResponse;
import com.ureca.ufit.domain.admin.service.AdminService;
import com.ureca.ufit.global.dto.CursorPageResponse;

import lombok.RequiredArgsConstructor;

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

}