package br.com.blocker.blockersapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BlockersApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlockersApiApplication.class, args);
	}
}