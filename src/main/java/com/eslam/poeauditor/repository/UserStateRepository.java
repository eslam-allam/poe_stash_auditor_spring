package com.eslam.poeauditor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eslam.poeauditor.model.UserState;

public interface UserStateRepository extends JpaRepository<UserState, Long> {

    UserState save(UserState userState);
    
}
