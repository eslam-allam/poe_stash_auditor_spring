package com.eslam.poeauditor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eslam.poeauditor.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
}
