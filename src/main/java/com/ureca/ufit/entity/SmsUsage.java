package com.ureca.ufit.entity;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sms_usages")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class SmsUsage extends UsageBase {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "sms_usage_id")
	private Long id;

	@ManyToOne(fetch = LAZY, optional = false)
	@JoinColumn(
		name = "user_id",
		referencedColumnName = "user_id"
	)
	private User user;

	@Builder(access = PRIVATE)
	private SmsUsage(Integer usageAmount, LocalDateTime usageMonth) {
		super(usageAmount, usageMonth);
		this.user = user;
	}

}
