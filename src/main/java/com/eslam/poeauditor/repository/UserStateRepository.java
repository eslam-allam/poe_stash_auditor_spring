package com.eslam.poeauditor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eslam.poeauditor.model.UserState;

import jakarta.transaction.Transactional;

public interface UserStateRepository extends JpaRepository<UserState, Long> {

    Optional<UserState> findByState(String state);

    @SuppressWarnings("unchecked")
    @Transactional
    UserState save(UserState userState);
}
