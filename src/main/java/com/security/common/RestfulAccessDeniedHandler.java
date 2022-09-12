package com.security.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.common.api.CommonResult;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * URL没有權限时，自行定義的返回结果
 */

@Slf4j
public class RestfulAccessDeniedHandler implements AccessDeniedHandler{
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {
    	
    	ObjectMapper mapper = new ObjectMapper(); 
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        
        log.info("RestfulAccessDeniedHandler: "+e.getMessage());
        
        response.getWriter().println(mapper.writeValueAsString(CommonResult.forbidden(e.getMessage())));
        response.getWriter().flush();
    }
}
