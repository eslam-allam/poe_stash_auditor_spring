package com.eslam.poeauditor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.eslam.poeauditor.model.UserState;

@Repository
public interface UserStateRepository extends JpaRepository<UserState, Long> {

    UserState save(UserState userState);
    
}
