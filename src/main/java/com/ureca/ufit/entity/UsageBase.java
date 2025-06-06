package com.ureca.ufit.entity;

import static lombok.AccessLevel.*;

import java.time.LocalDateTime;

import com.ureca.ufit.global.domain.TimeBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Getter
@NoArgsConstructor(access = PROTECTED)
public abstract class UsageBase extends TimeBaseEntity {

	@Positive
	@NotNull
	@Column(name = "usage_amount", nullable = false)
	private int usageAmount;

	@Column(name = "usage_month", nullable = false)
	private LocalDateTime usageMonth;

	protected UsageBase(Integer usageAmount, LocalDateTime usageMonth) {
		this.usageAmount = usageAmount;
		this.usageMonth = usageMonth;
	}
}
