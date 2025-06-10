package com.ureca.ufit.domain.ratePlan.entity;

import static lombok.AccessLevel.*;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.ureca.ufit.global.domain.MongoTimeBaseEntity;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "rate_plans")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class RatePlan extends MongoTimeBaseEntity {

	@Id
	@Field("rate_plan_id")
	private String id;

	@NotNull
	@Field("plan_name")
	private String planName;

	@NotNull
	@Field("summary")
	private String summary;

	@NotNull
	@Field("monthly_fee")
	private int monthlyFee;

	@NotNull
	@Field("discount_fee")
	private int discountFee;

	@NotNull
	@Field("data_allowance")
	private String dataAllowance;

	@NotNull
	@Field("voice_allowance")
	private String voiceAllowance;

	@NotNull
	@Field("sms_allowance")
	private String smsAllowance;

	@NotNull
	@Field("basic_benefit")
	private Map<String, Object> basicBenefit;

	@Field("special_benefit")
	private Map<String, Object> specialBenefit;

	@Field("discount_benefit")
	private Map<String, Object> discountBenefit;

	@NotNull
	@Field("is_enabled")
	private boolean isEnabled;

	@NotNull
	@Field("is_deleted")
	private boolean isDeleted;

	@Builder(access = PRIVATE)
	private RatePlan(String planName, String summary, int monthlyFee, int discountFee,
		String dataAllowance, String voiceAllowance, String smsAllowance,
		Map<String, Object> basicBenefit, Map<String, Object> specialBenefit, Map<String, Object> discountBenefit,
		boolean isEnabled, boolean isDeleted) {
		this.planName = planName;
		this.summary = summary;
		this.monthlyFee = monthlyFee;
		this.discountFee = discountFee;
		this.dataAllowance = dataAllowance;
		this.voiceAllowance = voiceAllowance;
		this.smsAllowance = smsAllowance;
		this.basicBenefit = basicBenefit;
		this.specialBenefit = specialBenefit;
		this.discountBenefit = discountBenefit;
		this.isEnabled = isEnabled;
		this.isDeleted = isDeleted;
	}

}
