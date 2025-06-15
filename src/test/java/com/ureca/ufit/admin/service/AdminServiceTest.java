package com.ureca.ufit.admin.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.ureca.ufit.domain.admin.dto.response.RatePlanMetricsItem;
import com.ureca.ufit.domain.admin.dto.response.RatePlanMetricsResponse;
import com.ureca.ufit.domain.admin.service.AdminService;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminServiceTest {

	@Autowired
	private AdminService adminService;

	@DisplayName("가입자 순으로 요금제 이름을 조회한다")
	@Test
	void getRatePlanMetrics(){
		// given

		// when
		RatePlanMetricsResponse response = adminService.getRatePlanMetrics(1, 10);

		List<RatePlanMetricsItem> items = response.item();

		// then
		assertThat(items).isSortedAccordingTo(
			Comparator.comparingInt(RatePlanMetricsItem::popularity).reversed()
		);
	}
}
