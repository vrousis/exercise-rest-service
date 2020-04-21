package com.rest.exercise.web;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class RequestDetailsHandlerInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestDetailsHandlerInterceptor.class);

    private final String X_TOKEN = "X-Token";

    private RequestHeaders requestHeaders;

    public RequestDetailsHandlerInterceptor(RequestHeaders requestHeaders){
        this.requestHeaders = Objects.requireNonNull(requestHeaders);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String xToken = request.getHeader(X_TOKEN);
        LOGGER.info("Handling request headers");

        if (Objects.isNull(xToken)) {
            throw new CustomAuthenticationException();
        }
        requestHeaders.setXToken(xToken);

        return super.preHandle(request, response, handler);
    }
}
