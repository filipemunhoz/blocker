package br.com.blocker.blockersapi.configuration;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@Configuration
public class R2dbcConfig extends AbstractR2dbcConfiguration {

    @Value("${spring.r2dbc.username}")
    private String username;

    @Value("${spring.r2dbc.password}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory(){
        ConnectionFactory connectionFactory = ConnectionFactories.get(ConnectionFactoryOptions.builder()
                .option(DRIVER,"mysql")
                .option(HOST,"localhost")
                .option(PORT,3306)
                .option(USER,username)
                .option(PASSWORD,password)
                .option(DATABASE,"blocker")
                .option(Option.valueOf("initialSize"),50)
                .option(Option.valueOf("maxSize"),100)
                .option(Option.valueOf("maximumPoolSize"),150)
                .option(Option.valueOf("validationQuery"),"SELECT 1")
                .option(Option.valueOf("maxLifeTime"),200)
                .build());

        ConnectionPoolConfiguration poolConfiguration = ConnectionPoolConfiguration.builder(connectionFactory)
                .maxSize(10)
                .initialSize(5)
                .maxIdleTime(Duration.ofMinutes(30))
                .maxCreateConnectionTime(Duration.ZERO)
                .build();

        return new ConnectionPool(poolConfiguration);
    }

    @Bean
    ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }
}