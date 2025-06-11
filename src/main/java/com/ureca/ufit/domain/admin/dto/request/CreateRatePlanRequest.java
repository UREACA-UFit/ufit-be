package com.ureca.ufit.domain.admin.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record CreateRatePlanRequest(
        String planName,
        String summary,
        int monthlyFee,
        int discountFee,
        String data_allowance,
        String voice_allowance,
        String sms_allowance,
        @JsonProperty("basic_benefit")
        Map<String, Object> basic_benefit,
        @JsonProperty("special_benefit")
        String special_benefit,
        @JsonProperty("discount_benefit")
        String discount_benefit
) {

}
