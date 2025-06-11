package com.ureca.ufit.domain.ratePlan.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ureca.ufit.domain.ratePlan.dto.response.RatePlanDetailResponse;
import com.ureca.ufit.domain.ratePlan.dto.response.RatePlanListResponse;
import com.ureca.ufit.domain.ratePlan.repository.RatePlanRepository;
import com.ureca.ufit.domain.ratePlan.service.RatePlanService;
import com.ureca.ufit.entity.RatePlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(RatePlanController.class)
@AutoConfigureMockMvc(addFilters = false)
class RatePlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatePlanService ratePlanService;

    private RatePlanListResponse listResponse1;
    private RatePlanListResponse listResponse2;
    private RatePlanDetailResponse detailResponse;


    @BeforeEach
    void setup() {
        listResponse1 = new RatePlanListResponse("1", "Basic Plan", 10000, 1000);
        listResponse2 = new RatePlanListResponse("2", "Premium Plan", 20000, 2000);
        detailResponse = new RatePlanDetailResponse(
                "1","Basic Plan",
                "Summary for Basic Plan",
                10000,
                1000,
                "10GB",
                "100 mins",
                "100 SMS",
                Collections.singletonMap("membership", "U+"),
                Collections.singletonMap("netflix", true),
                Collections.singletonMap("familyDiscount", 10)
        );
    }

    @Test
    @WithMockUser
    @DisplayName("GET /api/rateplans returns paginated list")
    void getRatePlans_ReturnsPagedList() throws Exception {
        Page<RatePlanListResponse> page = new PageImpl<>(List.of(listResponse1, listResponse2), PageRequest.of(0, 2), 2);
        given(ratePlanService.getRatePlanList(any(Pageable.class), eq("asc")))
                .willReturn(page);

        mockMvc.perform(get("/api/rateplans")
                        .param("type", "asc")
                        .param("page", "0")
                        .param("size", "2")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].planName").value("Basic Plan"))
                .andExpect(jsonPath("$.content[0].monthlyFee").value(10000))
                .andExpect(jsonPath("$.content[0].discountFee").value(1000));
    }

    @Test
    @WithMockUser
    @DisplayName("GET /api/rateplans/{id} returns plan detail")
    void getRatePlanById_ReturnsDetail() throws Exception {
        given(ratePlanService.getRatePlanDetail(eq("1"))).willReturn(detailResponse);

        mockMvc.perform(get("/api/rateplans/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.planName").value("Basic Plan"))
                .andExpect(jsonPath("$.summary").value("Summary for Basic Plan"))
                .andExpect(jsonPath("$.monthlyFee").value(10000))
                .andExpect(jsonPath("$.discountFee").value(1000))
                .andExpect(jsonPath("$.dataAllowance").value("10GB"))
                .andExpect(jsonPath("$.voiceAllowance").value("100 mins"))
                .andExpect(jsonPath("$.smsAllowance").value("100 SMS"))
                .andExpect(jsonPath("$.basicBenefit.membership").value("U+"))
                .andExpect(jsonPath("$.specialBenefit.netflix").value(true))
                .andExpect(jsonPath("$.discountBenefit.familyDiscount").value(10));
    }
}
