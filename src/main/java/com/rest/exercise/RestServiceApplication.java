
package com.rest.exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {
    "com.rest",
    "com.rest.exercise.web"
}, exclude = { CassandraDataAutoConfiguration.class })
@EntityScan(basePackages = {
    "com.rest.exercise.model"
})
public class RestServiceApplication {
    public static void main(final String[] args) {
        SpringApplication.run(RestServiceApplication.class, args);
    }
}

