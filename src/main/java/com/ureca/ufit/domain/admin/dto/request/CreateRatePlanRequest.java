package com.ureca.ufit.domain.admin.dto.request;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	Map<String, Object> special_benefit,
	@JsonProperty("discount_benefit")
	Map<String, Object> discount_benefit
) {

}
