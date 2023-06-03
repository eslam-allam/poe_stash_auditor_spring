package com.eslam.poeauditor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.github.cdimascio.dotenv.Dotenv;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;


@SpringBootApplication
@ComponentScan(basePackages = {"com.eslam.poeauditor.*"})
@EntityScan(basePackages =  {"com.eslam.poeauditor.*"})
@EnableJpaRepositories(basePackages = {"com.eslam.poeauditor.repository"})
@OpenAPIDefinition(info = @Info(title = "POE Stash Auditor API", version = "1.0", description = "An app that filters the user's stash items and sorts them based on price."))
@SecurityScheme(name = "base-user", scheme = "bearer", type = SecuritySchemeType.HTTP, in =  SecuritySchemeIn.HEADER)
@SecurityScheme(name = "admin", scheme = "bearer", type = SecuritySchemeType.HTTP, in =  SecuritySchemeIn.HEADER)
public class PoeStashAuditorApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		Dotenv environment = Dotenv.configure().directory(".").filename(".env").load();
		environment.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		SpringApplication.run(PoeStashAuditorApplication.class, args);
	}

}
