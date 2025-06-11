package com.ureca.ufit.domain.rateplan.exception;

import com.ureca.ufit.global.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum RatePlanErrorCode implements ErrorCode {

    RATEPLAN_NOT_FOUND("해당 ratePlanId의 요금제를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    RATEPLAN_ALREADY_DELETED("이미 삭제된 요금제입니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;

    RatePlanErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}