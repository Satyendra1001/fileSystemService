package com.typeface.assignment.filestorageservice.filters;

import com.typeface.assignment.filestorageservice.commons.constants.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.RequestFacade;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Slf4j
@Order(1)
public class RestDigestLoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ContentCachingResponseWrapper response = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);
        String traceId = ((RequestFacade) servletRequest).getHeader(CommonConstants.REQUEST_MSG_ID);
        MDC.put(CommonConstants.TRACE_ID,traceId);
        MDC.put(CommonConstants.REST_API,((RequestFacade) servletRequest).getRequestURI());

        Long startTime = System.currentTimeMillis();
        filterChain.doFilter(servletRequest,response);
        Long endTime = System.currentTimeMillis();

        Long responseTime = endTime-startTime;

        log.info("[REST API : {}], RESPONSE_TIME:{}",MDC.get(CommonConstants.REST_API),responseTime);
        response.copyBodyToResponse();
        MDC.clear();


    }
}
