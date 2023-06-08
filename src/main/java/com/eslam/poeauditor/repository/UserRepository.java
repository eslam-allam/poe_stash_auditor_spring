package com.eslam.poeauditor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eslam.poeauditor.constant.UserRoleCode;
import com.eslam.poeauditor.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUserName(String userName);
    Optional<User> findByEmailId(String emailId);
    Optional<User> findByUserRolesUserRoleCode(UserRoleCode userRoleCode);

    @SuppressWarnings("unchecked")
    User save(User user);
}
