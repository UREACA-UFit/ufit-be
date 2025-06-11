package com.ureca.ufit.entity;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.ureca.ufit.entity.enums.DataType;
import com.ureca.ufit.global.domain.TimeBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mobile_devices")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class MobileDevice extends TimeBaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "mobile_device_id")
	private Long id;

	@Size(max = 50)
	@NotNull
	@Column(name = "device_name", nullable = false)
	private String deviceName;

	@Enumerated(STRING)
	@NotNull
	@Column(name = "data_type", nullable = false)
	private DataType dataType;

	@ManyToOne(fetch = LAZY, optional = false)
	@JoinColumn(
		name = "user_id",
		referencedColumnName = "user_id"
	)
	private User user;

	@Builder(access = PRIVATE)
	private MobileDevice(String deviceName, DataType dataType) {
		this.deviceName = deviceName;
		this.dataType = dataType;
		this.user = user;
	}
}
