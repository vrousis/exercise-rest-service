package com.rest.exercise.web.config;


import com.rest.exercise.web.listeners.ContextShutdownListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContextShutdownListenerConfig {

    @Autowired
    private ContextShutdownListener contextShutdownListener;

    @Bean
    public WebServerFactoryCustomizer tomcatCustomizer() {
        return (WebServerFactory container) -> {
            if (container instanceof TomcatServletWebServerFactory) {
                ((TomcatServletWebServerFactory) container).addConnectorCustomizers(contextShutdownListener);
            }
        };
    }

}
