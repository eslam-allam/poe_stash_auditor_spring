package com.eslam.poeauditor.request;

import java.lang.reflect.Field;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.eslam.poeauditor.constant.GrantType;
import com.eslam.poeauditor.constant.Scope;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenRequest {
    
    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;

    @JsonProperty("grant_type")
    private GrantType grantType;

    @JsonProperty("code")
    private String code;

    @JsonProperty("redirect_uri")
    private String redirectUrl;
    
    @JsonProperty("scope")
    private Scope scope;

    @JsonProperty("code_verifier")
    private final String codeVerifier;

    public MultiValueMap<String, String> toMap() throws IllegalArgumentException, IllegalAccessException {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        for (Field field : this.getClass().getDeclaredFields()) {
            String fieldName = field.getName();
            if (field.isAnnotationPresent(JsonProperty.class)) {
                fieldName = field.getAnnotation(JsonProperty.class).value();
            }
            Object value = field.get(this);
            if (value instanceof Scope) {
                value = ((Scope) value).getScopeName();
            }
            if (value instanceof GrantType) {
                value = ((GrantType) value).getType();
            }
            map.add(fieldName, value.toString());
        }
        return map;
    }
}
