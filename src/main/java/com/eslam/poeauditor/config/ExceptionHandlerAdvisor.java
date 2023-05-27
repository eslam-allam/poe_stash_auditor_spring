package com.eslam.poeauditor.config;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.eslam.poeauditor.domain.ExceptionMessageDto;
import com.eslam.poeauditor.exception.UserAlreadyExistsException;

@RestControllerAdvice
public class ExceptionHandlerAdvisor extends ResponseEntityExceptionHandler{
    
    private final Logger log = LogManager.getLogger(getClass());

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionMessageDto generalExceptionHandler(Exception ex, WebRequest request) {
        log.error("A {} has occurred: {}", ex.getClass().getCanonicalName(), ex.getMessage());
        return ExceptionMessageDto.builder().errorMessage(ex.getMessage())
        .requestDetails(constructRequestDetails(request.getDescription(true)))
        .build();
    }
    
    @ExceptionHandler(value = {BadCredentialsException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionMessageDto badCredentialsExceptionHandler(BadCredentialsException ex, WebRequest request) {
        log.error("A BadCredentialsException has occurred: {}", ex.getMessage());
        return ExceptionMessageDto.builder().errorMessage(ex.getMessage())
        .requestDetails(constructRequestDetails(request.getDescription(true)))
        .build();
    }
    
    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionMessageDto userAlreadyExistsExceptionHandler(UserAlreadyExistsException ex, WebRequest request) {
        log.error("A UserAlreadyExistsException has occurred: {}", ex.getMessage());
        return ExceptionMessageDto.builder().errorMessage(ex.getMessage())
        .requestDetails(constructRequestDetails(request.getDescription(true)))
        .build();
    }

    private Map<String, String> constructRequestDetails(String details) {
        return Arrays.asList(StringUtils.split(details, ";"))
        .stream().collect(Collectors.toMap(n -> StringUtils.split(n, "=")[0],
        n -> StringUtils.split(n, "=")[1]));
    }
}
