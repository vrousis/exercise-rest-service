package com.rest.exercise.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app.server.graceful-shutdown")
public class GracefulShutdownPolicyConfiguration {

    private BackOffPolicy backOffPolicy = new BackOffPolicy();
    private RetryPolicy retryPolicy = new RetryPolicy();

    public static class BackOffPolicy {
        private int retryInterval;
        private int multiplier;
        private int maxRetryInterval;

        public int getRetryInterval() { return retryInterval; }

        public void setRetryInterval(int retryInterval) { this.retryInterval = retryInterval; }

        public int getMultiplier() { return multiplier; }

        public void setMultiplier(int multiplier) { this.multiplier = multiplier; }

        public int getMaxRetryInterval() { return maxRetryInterval; }

        public void setMaxRetryInterval(int maxRetryInterval) { this.maxRetryInterval = maxRetryInterval; }

        @Override
        public String toString() {
            return "BackOffPolicy{" +
                    "retryInterval='" + retryInterval + '\'' +
                    ", multiplier='" + multiplier + '\'' +
                    ", maxRetryInterval='" + maxRetryInterval + '\'' +
                    '}';
        }
    }

    public static class RetryPolicy {
        private int maxAttempts;

        public int getMaxAttempts() { return maxAttempts; }

        public void setMaxAttempts(int maxAttempts) { this.maxAttempts = maxAttempts; }

        @Override
        public String toString() {
            return "RetryPolicy{" +
                    "maxAttempts='" + maxAttempts + '\'' +
                    '}';
        }
    }

    public BackOffPolicy getBackOffPolicy() {
        return backOffPolicy;
    }

    public void setBackOffPolicy(BackOffPolicy backOffPolicy) {
        this.backOffPolicy = backOffPolicy;
    }

    public RetryPolicy getRetryPolicy() {
        return retryPolicy;
    }

    public void setRetryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
    }
}
