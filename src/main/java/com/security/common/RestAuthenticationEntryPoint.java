package com.security.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.common.api.CommonResult;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 尚未登錄或者token失效访问時，自行定義的返回结果
 */
@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    	
    	ObjectMapper mapper = new ObjectMapper();    	
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        log.info("RestAuthenticationEntryPoint: "+ authException.getMessage());
        response.getWriter().println(mapper.writeValueAsString(CommonResult.unauthorized(authException.getMessage())));
        response.getWriter().flush();
    }
}
