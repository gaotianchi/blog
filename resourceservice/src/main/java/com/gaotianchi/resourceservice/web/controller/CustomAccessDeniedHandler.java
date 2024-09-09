package com.gaotianchi.resourceservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaotianchi.resourceservice.persistence.enums.ResponseErrorCode;
import com.gaotianchi.resourceservice.web.response.APIResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        APIResponse<Void> apiResponse = APIResponse.fail(ResponseErrorCode.AUTHORIZATION_FAILED.getCode(), ResponseErrorCode.AUTHORIZATION_FAILED.getMessage());
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.getWriter().flush();
    }
}
