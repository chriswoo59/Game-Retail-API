package com.cognixia.jump;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info( title = "Game Retail API", version = "1.0"))
public class GameRetailApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameRetailApiApplication.class, args);
	}

}
