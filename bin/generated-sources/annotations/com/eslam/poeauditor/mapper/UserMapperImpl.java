package com.eslam.poeauditor.mapper;

import com.eslam.poeauditor.domain.UserDto;
import com.eslam.poeauditor.domain.UserRoleDto;
import com.eslam.poeauditor.domain.UserStateDto;
import com.eslam.poeauditor.model.User;
import com.eslam.poeauditor.model.UserRole;
import com.eslam.poeauditor.model.UserState;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-03T18:51:34+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.34.0.v20230511-1142, environment: Java 17.0.7 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto assemble(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setEmailId( user.getEmailId() );
        userDto.setUserName( user.getUserName() );
        userDto.setUserRoles( userRoleListToUserRoleDtoList( user.getUserRoles() ) );
        userDto.setUserStates( userStateListToUserStateDtoList( user.getUserStates() ) );

        return userDto;
    }

    @Override
    public List<UserDto> assembleUserDtoList(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( users.size() );
        for ( User user : users ) {
            list.add( assemble( user ) );
        }

        return list;
    }

    @Override
    public User assemble(UserDto user) {
        if ( user == null ) {
            return null;
        }

        User.UserBuilder user1 = User.builder();

        user1.emailId( user.getEmailId() );
        user1.userName( user.getUserName() );

        return user1.build();
    }

    @Override
    public List<User> assembleUserList(List<UserDto> users) {
        if ( users == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( users.size() );
        for ( UserDto userDto : users ) {
            list.add( assemble( userDto ) );
        }

        return list;
    }

    protected UserRoleDto userRoleToUserRoleDto(UserRole userRole) {
        if ( userRole == null ) {
            return null;
        }

        UserRoleDto userRoleDto = new UserRoleDto();

        userRoleDto.setDescription( userRole.getDescription() );
        userRoleDto.setUserRoleCode( userRole.getUserRoleCode() );

        return userRoleDto;
    }

    protected List<UserRoleDto> userRoleListToUserRoleDtoList(List<UserRole> list) {
        if ( list == null ) {
            return null;
        }

        List<UserRoleDto> list1 = new ArrayList<UserRoleDto>( list.size() );
        for ( UserRole userRole : list ) {
            list1.add( userRoleToUserRoleDto( userRole ) );
        }

        return list1;
    }

    protected UserStateDto userStateToUserStateDto(UserState userState) {
        if ( userState == null ) {
            return null;
        }

        UserStateDto userStateDto = new UserStateDto();

        userStateDto.setScope( userState.getScope() );
        userStateDto.setState( userState.getState() );

        return userStateDto;
    }

    protected List<UserStateDto> userStateListToUserStateDtoList(List<UserState> list) {
        if ( list == null ) {
            return null;
        }

        List<UserStateDto> list1 = new ArrayList<UserStateDto>( list.size() );
        for ( UserState userState : list ) {
            list1.add( userStateToUserStateDto( userState ) );
        }

        return list1;
    }
}
