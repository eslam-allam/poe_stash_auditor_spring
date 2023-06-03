package com.eslam.poeauditor.mapper;

import com.eslam.poeauditor.domain.UserRoleDto;
import com.eslam.poeauditor.model.UserRole;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-03T19:58:12+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.34.0.v20230511-1142, environment: Java 17.0.7 (Eclipse Adoptium)"
)
@Component
public class UserRoleMapperImpl implements UserRoleMapper {

    @Override
    public UserRoleDto assemble(UserRole userRole) {
        if ( userRole == null ) {
            return null;
        }

        UserRoleDto userRoleDto = new UserRoleDto();

        userRoleDto.setDescription( userRole.getDescription() );
        userRoleDto.setUserRoleCode( userRole.getUserRoleCode() );

        return userRoleDto;
    }

    @Override
    public List<UserRoleDto> assembleUserRoleDtoList(List<UserRole> userRoles) {
        if ( userRoles == null ) {
            return null;
        }

        List<UserRoleDto> list = new ArrayList<UserRoleDto>( userRoles.size() );
        for ( UserRole userRole : userRoles ) {
            list.add( assemble( userRole ) );
        }

        return list;
    }

    @Override
    public UserRole assemble(UserRoleDto userRoles) {
        if ( userRoles == null ) {
            return null;
        }

        UserRole.UserRoleBuilder userRole = UserRole.builder();

        userRole.description( userRoles.getDescription() );
        userRole.userRoleCode( userRoles.getUserRoleCode() );

        return userRole.build();
    }

    @Override
    public List<UserRole> assembleUserRoleList(List<UserRoleDto> userRoles) {
        if ( userRoles == null ) {
            return null;
        }

        List<UserRole> list = new ArrayList<UserRole>( userRoles.size() );
        for ( UserRoleDto userRoleDto : userRoles ) {
            list.add( assemble( userRoleDto ) );
        }

        return list;
    }
}
