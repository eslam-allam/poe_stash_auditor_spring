package com.eslam.poeauditor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eslam.poeauditor.model.AuthorizationGrant;

public interface UserAuthorizationCodeRepository extends JpaRepository<AuthorizationGrant, Long>{

    @SuppressWarnings("unchecked")
    AuthorizationGrant save(AuthorizationGrant userAuthorizationCode);
    
}
