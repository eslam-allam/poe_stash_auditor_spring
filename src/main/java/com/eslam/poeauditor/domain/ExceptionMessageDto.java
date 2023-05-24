package com.eslam.poeauditor.domain;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionMessageDto {

    @JsonProperty("error_message")
    private String errorMessage;

    @JsonProperty("request_details")
    private Map<String, String> requestDetails;
    
}
