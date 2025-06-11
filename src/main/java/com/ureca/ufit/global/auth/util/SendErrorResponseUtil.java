package com.ureca.ufit.global.auth.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ureca.ufit.global.exception.ErrorCode;
import com.ureca.ufit.global.exception.ErrorResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SendErrorResponseUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void sendErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();

        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}