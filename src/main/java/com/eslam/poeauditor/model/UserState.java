package com.eslam.poeauditor.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "user_state")
public class UserState {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "state")
    private String state;

    @Column(name = "code_verifier")
    private String codeVerifier;

    @Column(name = "code_challenge")
    private String codeChallenge;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



    public UserState() {
    }

    public UserState(Long id, String state, String codeVerifier, String codeChallenge, User user) {
        this.id = id;
        this.state = state;
        this.codeVerifier = codeVerifier;
        this.codeChallenge = codeChallenge;
        this.user = user;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCodeVerifier() {
        return this.codeVerifier;
    }

    public void setCodeVerifier(String codeVerifier) {
        this.codeVerifier = codeVerifier;
    }

    public String getCodeChallenge() {
        return this.codeChallenge;
    }

    public void setCodeChallenge(String codeChallenge) {
        this.codeChallenge = codeChallenge;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserState id(Long id) {
        setId(id);
        return this;
    }

    public UserState state(String state) {
        setState(state);
        return this;
    }

    public UserState codeVerifier(String codeVerifier) {
        setCodeVerifier(codeVerifier);
        return this;
    }

    public UserState codeChallenge(String codeChallenge) {
        setCodeChallenge(codeChallenge);
        return this;
    }

    public UserState user(User user) {
        setUser(user);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserState)) {
            return false;
        }
        UserState userState = (UserState) o;
        return Objects.equals(id, userState.id) && Objects.equals(state, userState.state) && Objects.equals(codeVerifier, userState.codeVerifier) && Objects.equals(codeChallenge, userState.codeChallenge) && Objects.equals(user, userState.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, state, codeVerifier, codeChallenge, user);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", state='" + getState() + "'" +
            ", codeVerifier='" + getCodeVerifier() + "'" +
            ", codeChallenge='" + getCodeChallenge() + "'" +
            ", user='" + getUser() + "'" +
            "}";
    }
    



}
