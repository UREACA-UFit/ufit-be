package com.ureca.ufit.domain.ratePlan.controller;

import java.util.Map;
import com.ureca.ufit.domain.ratePlan.repository.RatePlanRepository;
import com.ureca.ufit.entity.RatePlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class RatePlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RatePlanRepository ratePlanRepository;

    private String insertedId;

    @BeforeEach
    void setUp() {
        ratePlanRepository.deleteAll();

        // 테스트용 요금제 데이터 생성 및 삽입
        RatePlan plan = RatePlan.builder()
                .planName("유플러스 베이직")
                .summary("데이터 5GB, 통화 100분")
                .monthlyFee(33000)
                .discountFee(29000)
                .dataAllowance("5GB")
                .voiceAllowance("100분")
                .smsAllowance("기본제공")
                .basicBenefit(Map.of("혜택", "네이버페이 1,000P"))
                .specialBenefit(Map.of("서비스", "U+모바일tv 6개월"))
                .discountBenefit(Map.of("복지할인", "10%"))
                .isEnabled(true)
                .isDeleted(false)
                .build();

        RatePlan saved = ratePlanRepository.save(plan); // 저장 후 반환값에서 _id 추출
        insertedId = saved.getId();
    }

    @Test
    @DisplayName("요금제 목록 조회 성공") // 테스트 결과 리포트에 표시될 설명
    void testGetRatePlans() throws Exception {
        mockMvc.perform(get("/api/rateplans")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].planName").value("유플러스 베이직"))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.size").value(10));
    }

    @Test
    @DisplayName("요금제 상세 조회 성공")
    void testGetRatePlanById() throws Exception {
        // /api/rateplans/{id} 요청 → 응답 내용에 planName, monthlyFee가 올바르게 포함되어 있는지 확인
        mockMvc.perform(get("/api/rateplans/" + insertedId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.planName").value("유플러스 베이직"))
                .andExpect(jsonPath("$.monthlyFee").value(33000));
    }
}
