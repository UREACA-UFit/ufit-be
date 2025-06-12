package com.ureca.ufit.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AnswerType {

	RECOMMEND("요금제추천"),
	GENERAL("일반답변");

	private final String answerType;
}
