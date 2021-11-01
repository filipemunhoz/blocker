package br.com.blocker.blockersapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, RedisAutoConfiguration.class, RedisRepositoriesAutoConfiguration.class})
@EnableScheduling
public class BlockersApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlockersApiApplication.class, args);
	}
}