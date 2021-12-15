package com.wp.system;

import com.wp.system.request.auth.AuthRequest;
import com.wp.system.services.auth.AuthService;
import com.wp.system.services.user.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Random;

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

	@Autowired
	private AuthService authService;

	public static void main(String[] args) {
		System.out.println(System.getenv("SPRING_DATASOURCE_URL"));

//		this.authService.authUser(new AuthRequest("+75555555555", "dGVzdA=="));
		SpringApplication.run(SystemApplication.class, args);
	}

}
