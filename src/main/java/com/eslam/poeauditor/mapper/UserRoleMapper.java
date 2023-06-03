package com.eslam.poeauditor.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.eslam.poeauditor.domain.UserRoleDto;
import com.eslam.poeauditor.model.UserRole;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
    
    UserRoleDto assemble(UserRole userRole);
    List<UserRoleDto> assembleUserRoleDtoList(List<UserRole> userRoles);
    
    UserRole assemble(UserRoleDto userRoles);
    List<UserRole> assembleUserRoleList(List<UserRoleDto> userRoles);
}
