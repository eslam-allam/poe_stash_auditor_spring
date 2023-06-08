package com.eslam.poeauditor.model;

import org.hibernate.annotations.NaturalId;

import com.eslam.poeauditor.constant.Scope;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "user_state")
public class UserState {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(name = "state")
    private String state;

    @Column(name = "code_verifier")
    private String codeVerifier;

    @Column(name = "code_challenge")
    private String codeChallenge;

    @Column(name = "scope")
    private Scope scope;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="poe_user_id", nullable=false)
    private User user;

    @Builder
    public UserState(String state, String codeVerifier, String codeChallenge, Scope scope, User user) {
        this.state = state;
        this.codeVerifier = codeVerifier;
        this.codeChallenge = codeChallenge;
        this.scope = scope;
        this.user = user;
    }
}
