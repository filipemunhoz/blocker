package br.com.blocker.blockersapi;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.scheduling.annotation.EnableScheduling;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@SpringBootApplication
@EnableScheduling
public class BlockersApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlockersApiApplication.class, args);
	}
}