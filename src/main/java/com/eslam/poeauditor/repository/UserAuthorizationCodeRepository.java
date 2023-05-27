package com.eslam.poeauditor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eslam.poeauditor.model.UserAuthorizationCode;

public interface UserAuthorizationCodeRepository extends JpaRepository<UserAuthorizationCode, Long>{

    @SuppressWarnings("unchecked")
    UserAuthorizationCode save(UserAuthorizationCode userAuthorizationCode);
    
}
