package com.rest.exercise.web.listeners;

import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Component
public class ContextShutdownListener implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {

    private final static Logger LOGGER = LoggerFactory.getLogger(ContextShutdownListener.class);

    private volatile Connector connector;

    @Autowired
    private RetryTemplate retryTemplate;

    /**
     * The number of seconds to wait for active threads to finish before shutting down the embedded web container.
     */
    @Value("${app.server.contextShutdown.awaitPeriod:10}")
    private int awaitPeriod;


    private class RecoveryAwaitThreadsCallback implements RecoveryCallback<Boolean> {
        @Override
        public Boolean recover(RetryContext context) {
            LOGGER.info("Attempts exhausted. Total: " + context.getRetryCount());
            LOGGER.warn("Last exception: " + Optional.ofNullable(context.getLastThrowable())
                    .orElse(new Throwable("No exception thrown")).getMessage());
            return false;
        }
    }

    private class RetryAwaitThreadsCallback implements RetryCallback<Boolean, Exception> {
        @Override
        public Boolean doWithRetry(RetryContext context) throws Exception {
            LOGGER.warn("Exception callback: attempt " + context.getRetryCount());
            if (shutdownThreadPool())
                return true;
            throw new Exception("Shutdown is not completed");
        }
    }

    private boolean shutdownThreadPool() {

        Executor executor = this.connector.getProtocolHandler().getExecutor();
        if (executor instanceof ThreadPoolExecutor) {
            try {
                ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
                threadPoolExecutor.shutdown();

                if (threadPoolExecutor.awaitTermination(awaitPeriod, TimeUnit.SECONDS)) {
                    LOGGER.info("Tomcat thread pool shutdown completed");
                    return true;
                }
                else {
                    LOGGER.info("Tomcat thread pool did not shutdown gracefully within {} secs.", awaitPeriod);
                    return false;
                }

            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        return true;
    }

    private boolean isTomcatConnector() {
        if (this.connector == null) {
            // No Tomcat connector
            return false;
        }
        return true;
    }

    private boolean isMainContextClosingEvent(ContextClosedEvent event) {
        if (event.getApplicationContext().getParent() != null) {
            // This is the child context for the DispatcherServlet
            return false;
        }
        //Only handle the shutdown event of the main context
        return true;
    }

    /**
     * Pause the Tomcat connector(rejects new requests) and respond to any
     * in-flight requests before allowing the application-context to shutdown
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {

        if (!isMainContextClosingEvent(event)) {
            return;
        }

        if (!isTomcatConnector()) {
            return;
        }

        LOGGER.info("Attempting graceful shutdown (graceful period : {} secs)", awaitPeriod);
        this.connector.pause();
        try {
            retryTemplate.execute(new RetryAwaitThreadsCallback(),new RecoveryAwaitThreadsCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Closing context");

    }

    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }

}