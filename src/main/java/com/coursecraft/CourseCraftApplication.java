package com.coursecraft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CourseCraftApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseCraftApplication.class, args);
	}

}
