package com.ureca.ufit.domain.ratePlan.dto.response;




import com.ureca.ufit.entity.RatePlan;

import java.util.Map;


/**
 * 요금제 상세 정보
 */

public record RatePlanDetailResponse(
        String id,
        String planName,
        String summary,
        int monthlyFee,
        int discountFee,
        String dataAllowance,
        String voiceAllowance,
        String smsAllowance,

        // 다양한 혜택(기본/특별/할인)을 JSON 형태로 Map으로 표현
        Map<String, Object> basicBenefit,     // 기본 제공 혜택 (예: 멤버십 등)
        Map<String, Object> specialBenefit,   // 특별 혜택 (예: 넷플릭스 제공 등)
        Map<String, Object> discountBenefit   // 할인 관련 정보 (예: 가족 결합 할인 등)
) {
    public static RatePlanDetailResponse from(RatePlan ratePlan) {
        return new RatePlanDetailResponse(
                ratePlan.getId(),
                ratePlan.getPlanName(),
                ratePlan.getSummary(),
                ratePlan.getMonthlyFee(),
                ratePlan.getDiscountFee(),
                ratePlan.getDataAllowance(),
                ratePlan.getVoiceAllowance(),
                ratePlan.getSmsAllowance(),
                ratePlan.getBasicBenefit(),
                ratePlan.getSpecialBenefit(),
                ratePlan.getDiscountBenefit()
        );
    }
}
