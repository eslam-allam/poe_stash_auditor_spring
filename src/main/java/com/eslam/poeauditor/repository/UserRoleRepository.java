package com.eslam.poeauditor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eslam.poeauditor.constant.UserRoleCode;
import com.eslam.poeauditor.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
    Optional<UserRole> findByUserRoleCode(UserRoleCode userRoleCode);
}
