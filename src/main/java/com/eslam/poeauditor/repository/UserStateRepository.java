package com.eslam.poeauditor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eslam.poeauditor.model.UserState;

public interface UserStateRepository extends JpaRepository<UserState, Long> {

    Optional<UserState> findByState(String state);

    @SuppressWarnings("unchecked")
    UserState save(UserState userState);
}
