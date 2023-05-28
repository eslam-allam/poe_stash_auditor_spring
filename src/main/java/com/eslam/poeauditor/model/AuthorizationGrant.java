package com.eslam.poeauditor.model;

import java.util.Date;
import com.eslam.poeauditor.constant.Scope;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "authorization_grant")
public class AuthorizationGrant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "expires_at")
    private Date expiresAt;

    @Column(name = "token_type")
    private String tokenType;

    @Column(name = "scope")
    private Scope scope;

    @Column(name = "sub")
    private String sub;

    @Column(name = "refresh_token")
    private String refreshToken;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="poe_user_id", nullable=false)
    private User user;


    @Builder
    public AuthorizationGrant(String accessToken, Date expiresAt, String tokenType, Scope scope, 
    String sub, String refreshToken, User user) {
        this.accessToken = accessToken;
        this.expiresAt = expiresAt;
        this.tokenType = tokenType;
        this.scope = scope;
        this.sub = sub;
        this.refreshToken = refreshToken;
        this.user = user;
    }
    
}
