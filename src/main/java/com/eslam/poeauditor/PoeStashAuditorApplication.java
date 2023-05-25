package com.eslam.poeauditor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.github.cdimascio.dotenv.Dotenv;


@SpringBootApplication
@ComponentScan(basePackages = {"com.eslam.poeauditor.*"})
@EntityScan(basePackages =  {"com.eslam.poeauditor.*"})
@EnableJpaRepositories(basePackages = {"com.eslam.poeauditor.repository"})
public class PoeStashAuditorApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		Dotenv environment = Dotenv.configure().directory(".").filename(".env").load();
		environment.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		SpringApplication.run(PoeStashAuditorApplication.class, args);
	}

}
