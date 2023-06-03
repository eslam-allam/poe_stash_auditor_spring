package com.eslam.poeauditor.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.eslam.poeauditor.domain.UserStateDto;
import com.eslam.poeauditor.model.UserState;

@Mapper(componentModel = "spring")
public interface UserStateMapper {
    
    UserStateDto assemble(UserState userState);
    List<UserStateDto> assembleUserStateList(List<UserState> userStates);
    
    UserState assemble(UserStateDto userState);
    List<UserState> assembleUserStateDtoList(List<UserStateDto> userStates);
}
