package com.trailerplan.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.test.context.ContextConfiguration;

import com.trailerplan.config.*;


@Configuration
@ComponentScan(basePackages = {"com.trailerplan.repository", "com.trailerplan.service", "com.trailerplan.controller"})
@ContextConfiguration(classes = {AppConfig.class, R2dbcConfig.class, SwaggerConfig.class})
@SpringBootApplication(scanBasePackages = {"com.trailerplan.service", "com.trailerplan.repository", "com.trailerplan.controller"})
@EnableR2dbcRepositories(basePackages = {"com.trailerplan.repository"})
@Slf4j
public class RaceApiReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(com.trailerplan.application.RaceApiReactiveApplication.class, args);
	}
}
