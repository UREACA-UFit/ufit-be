package com.ureca.ufit.global.auth.filter;

import com.ureca.ufit.global.auth.util.SendErrorResponseUtil;
import com.ureca.ufit.global.exception.CommonErrorCode;
import com.ureca.ufit.global.exception.RestApiException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (RestApiException e) {
            SendErrorResponseUtil.sendErrorResponse(response, e.getErrorCode());
        } catch (Exception e) {
            SendErrorResponseUtil.sendErrorResponse(response, CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
