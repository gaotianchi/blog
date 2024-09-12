package com.gaotianchi.resource.web.controller.hook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaotianchi.resource.persistence.enums.ResponseErrorCode;
import com.gaotianchi.resource.web.response.APIResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BeforeRequestHookAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        APIResponse<Void> apiResponse = APIResponse.fail(ResponseErrorCode.AUTHENTICATION_FAILED.getCode(), ResponseErrorCode.AUTHENTICATION_FAILED.getMessage());
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.getWriter().flush();
    }
}
