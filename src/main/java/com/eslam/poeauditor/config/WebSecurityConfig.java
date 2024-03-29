package com.eslam.poeauditor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.eslam.poeauditor.constant.UserRoleCode;
import com.eslam.poeauditor.jwt.JWTAuthenticationFilter;
import com.eslam.poeauditor.service.PoeUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class WebSecurityConfig{

    private static final String[] SECURED_URLs = {};

    private static final String[] UN_SECURED_URLs = {
            "/security/authenticate",
            "/security/register",
            "/authorization/granted", "/api-docs","/swagger-ui/*", "/v3/api-docs/*", "/v3/api-docs"
    };

    @Autowired
    private JWTAuthenticationFilter authenticationFilter;

    @Autowired
    private PoeUserDetailsService userDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
       return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests.requestMatchers(UN_SECURED_URLs).permitAll()
                ).authorizeHttpRequests(authorizeRequests ->
                authorizeRequests.requestMatchers(SECURED_URLs)
                .hasAuthority(UserRoleCode.ADMIN.toString()).anyRequest().authenticated())
                .sessionManagement(sessionManagement -> 
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}