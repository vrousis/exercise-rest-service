package com.rest.exercise.web;

import static com.rest.exercise.web.controllers.ExerciseRestController.RETRIEVE_ITEM_FOR_CUSTOMER_REGEX;
import static com.rest.exercise.web.controllers.ExerciseRestController.RETRIEVE_ITEM_REGEX;
import static com.rest.exercise.web.controllers.ExerciseRestController.SET_ITEM_FOR_CUSTOMER_REGEX;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.MappedInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebConfig.class);

    @Autowired
    private final RequestHeaders requestHeaders;

    @Autowired
    public WebConfig (RequestHeaders requestHeaders){
        this.requestHeaders = Objects.requireNonNull(requestHeaders);
    }

    @Bean
    public MappedInterceptor requestDetailsHandlerInterceptor(RequestHeaders requestHeaders) {
        return new MappedInterceptor(new String[]{RETRIEVE_ITEM_REGEX,RETRIEVE_ITEM_FOR_CUSTOMER_REGEX,SET_ITEM_FOR_CUSTOMER_REGEX},
                new RequestDetailsHandlerInterceptor(requestHeaders));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Custom interceptor, add intercept path and exclude intercept path
        registry.addInterceptor(new RequestDetailsHandlerInterceptor(requestHeaders)).addPathPatterns("/**");
    }

}
