package com.rest.exercise.cassandra;

import java.util.Objects;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.AsyncCassandraTemplate;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.datastax.driver.core.QueryLogger;
import com.datastax.driver.core.Session;

@Configuration
@EnableCassandraRepositories( basePackages = { "com.rest.core" } )
public class CassandraConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(CassandraConfig.class);

    @Value("${cassandra.keyspace}")
    private String keyspace;

    @Value("${cassandra.port}")
    private Integer port;

    @Value("${cassandra.contactpoints}")
    private String contactpoints;

    @PostConstruct
    private void init() {
        QueryLogger queryLogger = QueryLogger.builder().withConstantThreshold(100).build();
        cluster().getObject().register(queryLogger);
    }

    @Bean
    public CassandraClusterFactoryBean cluster() {
        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints(contactpoints);
        cluster.setPort(port);
        return cluster;
    }

    @Bean
    public CassandraMappingContext mappingContext() {
        CassandraMappingContext mappingContext =  new CassandraMappingContext();
        mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(Objects.requireNonNull(cluster().getObject()), keyspace));
        return mappingContext;
    }

    @Bean
    public CassandraConverter converter() {
        return new MappingCassandraConverter(mappingContext());
    }

    @Bean
    public CassandraSessionFactoryBean session() {
        CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
        session.setCluster(Objects.requireNonNull(cluster().getObject()));
        session.setKeyspaceName(keyspace);
        session.setConverter(converter());
        session.setSchemaAction(SchemaAction.NONE);
        return session;
    }

    @Bean
    public CassandraOperations cassandraTemplate() throws Exception {
        return new CassandraTemplate(session().getObject(), converter());
    }

    @Bean
    AsyncCassandraTemplate asyncCassandraTemplate(Session session) {
        return new AsyncCassandraTemplate(session, converter());
    }

}