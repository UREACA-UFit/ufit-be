package com.ureca.ufit.rateplan.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.ureca.ufit.common.support.TestContainerSupport;
import com.ureca.ufit.domain.admin.dto.response.AdminRatePlanResponse;
import com.ureca.ufit.domain.rateplan.repository.RatePlanQueryRepository;
import com.ureca.ufit.global.dto.CursorPageResponse;

@SpringBootTest
class RatePlanQueryRepositoryTest extends TestContainerSupport {

	@Autowired
	RatePlanQueryRepository ratePlanQueryRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	@DisplayName("커서 기반으로 요금제 목록을 조회한다")
	@Test
	void getRatePlansByCursor() {
		// given
		final int START_INDEX = 1;
		final int END_INDEX = 10;
		final int SIZE = 3;
		final String TYPE = "date";
		final String PLAN = "Plan ";
		final String CREATED_AT = "createdAt";

		List<Document> docs = new ArrayList<>();
		for (int i = START_INDEX; i <= END_INDEX; i++) {
			docs.add(new Document()
				.append("plan_name", PLAN + i)
				.append("summary", "Summary " + i)
				.append("monthly_fee", i * 1000)
				.append("discount_fee", i * 100)
				.append("data_allowance", "10GB")
				.append("voice_allowance", "100min")
				.append("sms_allowance", "100")
				.append("basic_benefit", Map.of("benefit", "basic"))
				.append("special_benefit", Map.of("benefit", "special"))
				.append("discount_benefit", Map.of("benefit", "discount"))
				.append("is_enabled", true)
				.append("is_deleted", false)
				.append(CREATED_AT, LocalDateTime.now().minusDays(10 - i))
				.append("updatedAt", LocalDateTime.now().minusDays(10 - i))
			);
		}
		mongoTemplate.getDb().getCollection("rate_plans").insertMany(docs);

		// when
		CursorPageResponse<AdminRatePlanResponse> response = ratePlanQueryRepository.getRatePlansByCursor(null, SIZE,
			TYPE);

		// then
		Assertions.assertAll(
			() -> assertThat(response.item().size()).isEqualTo(SIZE),
			() -> assertThat(response.item().get(0).planName()).isEqualTo(PLAN + END_INDEX),
			() -> assertThat(response.hasNext()).isTrue(),
			() -> {
				LocalDateTime expected = ((LocalDateTime)docs.get(END_INDEX - SIZE - 1).get(CREATED_AT))
					.truncatedTo(ChronoUnit.MILLIS);
				DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
				assertThat(LocalDateTime.parse(response.nextCursor(), fmt)).isEqualTo(expected);

				assertThat(response.nextCursor()).isEqualTo(docs.get(END_INDEX - SIZE - 1).get(CREATED_AT).toString());
			}
		);

	}
}
