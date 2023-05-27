package com.eslam.poeauditor.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.NaturalId;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "poe_user")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @NaturalId
    @Column(name = "email_id", nullable = false)
    private String emailId;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles")
    private List<UserRole> userRoles;

    @OneToMany(fetch = FetchType.EAGER)
    private List<UserState> userStates;

    @Column(name = "is_enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "is_expired", nullable = false)
    private Boolean expired = false;

    @Column(name = "is_locked", nullable = false)
    private Boolean locked = false;

    @Builder
    public User(String userName, String emailId, String password, List<UserRole> roles) {
        this.userName = userName;
        this.emailId = emailId;
        this.password = password;
        this.userRoles = roles;
    }
    
    public void addRole (UserRole userRole) {
        if (this.userRoles == null) {
            this.userRoles = new ArrayList<>();
        }
        this.userRoles.add(userRole);
    }
    
    public void addUserState (UserState userState) {
        if (this.userStates == null) {
            this.userStates = new ArrayList<>();
        }
        this.userStates.add(userState);
    }
}
