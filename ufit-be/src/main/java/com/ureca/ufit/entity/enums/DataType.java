package com.ureca.ufit.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DataType {

	FIVE_G("5G"),
	LTE("LTE"),
	;

	private final String type;
}
