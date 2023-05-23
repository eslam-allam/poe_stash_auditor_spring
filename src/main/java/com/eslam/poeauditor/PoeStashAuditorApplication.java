package com.eslam.poeauditor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.eslam.poeauditor.model.UserState;
import com.eslam.poeauditor.repository.UserRepository;
import com.eslam.poeauditor.repository.UserStateRepository;

@SpringBootApplication
@ComponentScan(basePackages = {"com.eslam.poeauditor.*"})
@EntityScan(basePackages =  {"com.eslam.poeauditor.*"})
@EnableJpaRepositories(basePackages = {"com.eslam.poeauditor.repository"})
public class PoeStashAuditorApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(PoeStashAuditorApplication.class, args);
	}

}
