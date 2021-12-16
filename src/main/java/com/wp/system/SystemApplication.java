package com.wp.system;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@SecurityScheme(
		name = "Bearer",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
)
@OpenAPIDefinition(
		info = @Info(
				title = "WP API",
				version = "1.0",
				description = "WP system")
)
@EnableScheduling
@ComponentScan(basePackages = "com.wp.system.*")
public class SystemApplication {

	public static void main(String[] args) {
		System.out.println(System.getenv("SPRING_DATASOURCE_URL"));

		SpringApplication.run(SystemApplication.class, args);
	}

}
