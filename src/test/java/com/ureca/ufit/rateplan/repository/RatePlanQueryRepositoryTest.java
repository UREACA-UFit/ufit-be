package com.ureca.ufit.rateplan.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ureca.ufit.common.fixture.RatePlanFixture;
import com.ureca.ufit.common.support.DataMongoSupport;
import com.ureca.ufit.domain.admin.dto.response.AdminRatePlanResponse;
import com.ureca.ufit.domain.rateplan.repository.RatePlanQueryRepositoryImpl;
import com.ureca.ufit.domain.rateplan.repository.RatePlanRepository;
import com.ureca.ufit.entity.RatePlan;
import com.ureca.ufit.global.dto.CursorPageResponse;

class RatePlanQueryRepositoryTest extends DataMongoSupport {

	@Autowired
	RatePlanQueryRepositoryImpl ratePlanQueryRepositoryImpl;

	@Autowired
	RatePlanRepository ratePlanRepository;

	@AfterEach
	void tearDown() {
		ratePlanRepository.deleteAll();
	}

	@DisplayName("커서 기반으로 요금제 목록을 조회한다")
	@Test
	void getRatePlansByCursor() {
		// given
		final int SIZE = 2;
		final String TYPE = "lowestPrice";

		RatePlan plan1 = RatePlanFixture.ratePlan("plan1", 100);
		RatePlan plan2 = RatePlanFixture.ratePlan("plan2", 500);
		RatePlan plan3 = RatePlanFixture.ratePlan("plan3", 900);
		RatePlan plan4 = RatePlanFixture.ratePlan("plan4", 700);
		RatePlan plan5 = RatePlanFixture.ratePlan("plan5", 300);
		List<RatePlan> ratePlans = ratePlanRepository.saveAll(List.of(plan1, plan2, plan3, plan4, plan5));

		CursorPageResponse<AdminRatePlanResponse> response1 = ratePlanQueryRepositoryImpl.getRatePlansByCursor(
			null,
			SIZE,
			TYPE
		);

		// when
		CursorPageResponse<AdminRatePlanResponse> response2 = ratePlanQueryRepositoryImpl.getRatePlansByCursor(
			response1.nextCursor(),
			SIZE,
			TYPE
		);

		// then
		assertAll(
			() -> assertThat(response2.item().size()).isEqualTo(SIZE),
			() -> assertThat(response2.item().get(SIZE - 1).planName()).isEqualTo(plan4.getPlanName()),
			() -> assertThat(response2.nextCursor()).isEqualTo(plan4.getMonthlyFee() + "/" + plan4.getId())
		);
	}

	@DisplayName("낮은 가격 순으로 요금제 목록을 조회한다.")
	@Test
	void getRatePlansOrderByLowestPrice() {
		// given
		final int SIZE = 2;
		final String TYPE = "lowestPrice";

		RatePlan plan1 = RatePlanFixture.ratePlan("plan1", 100);
		RatePlan plan2 = RatePlanFixture.ratePlan("plan2", 500);
		RatePlan plan3 = RatePlanFixture.ratePlan("plan3", 900);
		RatePlan plan4 = RatePlanFixture.ratePlan("plan4", 700);
		RatePlan plan5 = RatePlanFixture.ratePlan("plan5", 300);
		ratePlanRepository.saveAll(List.of(plan1, plan2, plan3, plan4, plan5));

		// when
		CursorPageResponse<AdminRatePlanResponse> response = ratePlanQueryRepositoryImpl.getRatePlansByCursor(
			null,
			SIZE,
			TYPE
		);

		// then
		assertAll(
			() -> assertThat(response.item().size()).isEqualTo(SIZE),
			() -> assertThat(response.item().get(SIZE - 1).planName()).isEqualTo(plan5.getPlanName())
		);
	}

	@DisplayName("높은 가격 순으로 요금제 목록을 조회한다.")
	@Test
	void getRatePlansOrderByHighestPrice() {
		// given
		final int SIZE = 2;
		final String TYPE = "highestPrice";

		RatePlan plan1 = RatePlanFixture.ratePlan("plan1", 100);
		RatePlan plan2 = RatePlanFixture.ratePlan("plan2", 500);
		RatePlan plan3 = RatePlanFixture.ratePlan("plan3", 900);
		RatePlan plan4 = RatePlanFixture.ratePlan("plan4", 700);
		RatePlan plan5 = RatePlanFixture.ratePlan("plan5", 300);
		ratePlanRepository.saveAll(List.of(plan1, plan2, plan3, plan4, plan5));

		// when
		CursorPageResponse<AdminRatePlanResponse> response = ratePlanQueryRepositoryImpl.getRatePlansByCursor(
			null,
			SIZE,
			TYPE
		);

		// then
		assertAll(
			() -> assertThat(response.item().size()).isEqualTo(SIZE),
			() -> assertThat(response.item().get(SIZE - 1).planName()).isEqualTo(plan4.getPlanName())
		);
	}

	@DisplayName("요금제 목록이 비어있을 때 빈 목록이 조회된다.")
	@Test
	void getEmptyWhenRatePlanIsEmpty() {
		// given
		final int SIZE = 10;
		final String TYPE = "highestPrice";

		// when
		CursorPageResponse<AdminRatePlanResponse> response = ratePlanQueryRepositoryImpl.getRatePlansByCursor(
			null,
			SIZE,
			TYPE
		);

		// then
		assertAll(
			() -> assertThat(response.item().size()).isZero(),
			() -> assertThat(response.hasNext()).isFalse(),
			() -> assertThat(response.nextCursor()).isNull()
		);
	}
}
