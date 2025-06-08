package com.ureca.ufit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableJpaAuditing
@EnableMongoAuditing
@SpringBootApplication
public class UfitApplication {

	public static void main(String[] args) {
		SpringApplication.run(UfitApplication.class, args); //커밋 테스트용 주석
	}

}
