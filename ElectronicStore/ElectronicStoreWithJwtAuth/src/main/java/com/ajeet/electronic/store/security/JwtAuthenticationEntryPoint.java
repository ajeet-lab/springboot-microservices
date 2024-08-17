package com.ajeet.electronic.store.security;

import com.ajeet.electronic.store.helpers.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();

        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .isSuccess(false)
                .message(authException.getMessage())
                .build();

        ObjectMapper mapper = new ObjectMapper();
        writer.print(mapper.writeValueAsString(apiResponse));
        //writer.print("Access Denied " + authException.getMessage());
    }
}
