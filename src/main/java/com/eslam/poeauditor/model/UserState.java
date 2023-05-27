package com.eslam.poeauditor.model;

import org.hibernate.annotations.NaturalId;

import com.eslam.poeauditor.constant.Scope;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Accessors(fluent = true)
@NoArgsConstructor
@Table(name = "user_state")
public class UserState {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @JoinTable(name = "user_states")
    private User user;

    @Builder
    public UserState(String state, String codeVerifier, String codeChallenge, Scope scope) {
        this.state = state;
        this.codeVerifier = codeVerifier;
        this.codeChallenge = codeChallenge;
        this.scope = scope;
    }
}
