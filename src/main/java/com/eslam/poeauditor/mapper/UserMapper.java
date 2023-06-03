package com.eslam.poeauditor.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.eslam.poeauditor.domain.UserDto;
import com.eslam.poeauditor.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto assemble(User user);
    List<UserDto> assembleUserDtoList(List<User> users);

    User assemble(UserDto user);
    List<User> assembleUserList(List<UserDto> users);
}
