package com.ureca.ufit.domain.ratePlan.dto.response;


import com.ureca.ufit.entity.RatePlan;

/**
 * 요금제 목록 화면에 노출할 정보
 */
public record RatePlanListResponse(
        String id,
        String planName,
        int monthlyFee,
        int discountFee
) {
    public static RatePlanListResponse from(RatePlan ratePlan) {
        return new RatePlanListResponse(
                ratePlan.getId(),
                ratePlan.getPlanName(),
                ratePlan.getMonthlyFee(),
                ratePlan.getDiscountFee()
        );
    }
}
