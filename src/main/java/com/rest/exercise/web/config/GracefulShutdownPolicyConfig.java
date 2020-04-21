package com.rest.exercise.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;

import java.util.concurrent.TimeUnit;

import org.springframework.retry.support.RetryTemplate;

@Configuration
public class GracefulShutdownPolicyConfig {

    @Autowired
    GracefulShutdownPolicyConfiguration gracefulShutdownPolicyConfiguration;

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retTempl = new RetryTemplate();

        retTempl.setRetryPolicy(setSimpleRetryPolicy());
        retTempl.setBackOffPolicy(setExponentialBackOffPolicy());

        return retTempl;
    }

    @Bean
    public BackOffPolicy setExponentialBackOffPolicy() {
        ExponentialBackOffPolicy expBackOffPolicy = new ExponentialBackOffPolicy();
        expBackOffPolicy.setInitialInterval(TimeUnit.SECONDS.toMillis(gracefulShutdownPolicyConfiguration.getBackOffPolicy().getRetryInterval()));
        expBackOffPolicy.setMaxInterval(TimeUnit.SECONDS.toMillis(gracefulShutdownPolicyConfiguration.getBackOffPolicy().getMaxRetryInterval()));
        expBackOffPolicy.setMultiplier(gracefulShutdownPolicyConfiguration.getBackOffPolicy().getMultiplier());
        return expBackOffPolicy;
    }

    @Bean
    public RetryPolicy setSimpleRetryPolicy() {
        SimpleRetryPolicy sRetryPolicy = new SimpleRetryPolicy();
        sRetryPolicy.setMaxAttempts(gracefulShutdownPolicyConfiguration.getRetryPolicy().getMaxAttempts());
        return sRetryPolicy;
    }

}
