package com.ureca.ufit.domain.admin.exception;

import com.ureca.ufit.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@RequiredArgsConstructor
@Getter
public enum RatePlanErrorCode implements ErrorCode {
    RATEPLAN_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 rateplanId의 요금제를 찾을 수 없습니다.");
//    FAIL_RATE_PLAN_METRICS(HttpStatus.NOT_FOUND, "요금제 지표 조회에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
