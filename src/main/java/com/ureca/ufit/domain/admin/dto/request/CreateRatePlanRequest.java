package com.ureca.ufit.domain.admin.dto.request;

import java.time.LocalDateTime;
import java.util.Map;

public record CreateRatePlanRequest(
    String ratePlanId,
    String planName,
    String summary,
    int monthlyFee,
    int discountFee,
    String data_allowance,
    String voice_allowance,
    String sms_allowance,
    Map<String, Object> basic_benefit,
    String special_benefit,
    String discount_benefit,
    LocalDateTime createdAt
) {

}
