package com.eslam.poeauditor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eslam.poeauditor.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUserName(String userName);
    Optional<User> findByEmailId(String emailId);

    @SuppressWarnings("unchecked")
    User save(User user);
}
