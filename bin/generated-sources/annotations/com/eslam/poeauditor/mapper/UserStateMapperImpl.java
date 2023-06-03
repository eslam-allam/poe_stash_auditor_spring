package com.eslam.poeauditor.mapper;

import com.eslam.poeauditor.domain.UserStateDto;
import com.eslam.poeauditor.model.UserState;
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
public class UserStateMapperImpl implements UserStateMapper {

    @Override
    public UserStateDto assemble(UserState userState) {
        if ( userState == null ) {
            return null;
        }

        UserStateDto userStateDto = new UserStateDto();

        userStateDto.setScope( userState.getScope() );
        userStateDto.setState( userState.getState() );

        return userStateDto;
    }

    @Override
    public List<UserStateDto> assembleUserStateList(List<UserState> userStates) {
        if ( userStates == null ) {
            return null;
        }

        List<UserStateDto> list = new ArrayList<UserStateDto>( userStates.size() );
        for ( UserState userState : userStates ) {
            list.add( assemble( userState ) );
        }

        return list;
    }

    @Override
    public UserState assemble(UserStateDto userState) {
        if ( userState == null ) {
            return null;
        }

        UserState.UserStateBuilder userState1 = UserState.builder();

        userState1.scope( userState.getScope() );
        userState1.state( userState.getState() );

        return userState1.build();
    }

    @Override
    public List<UserState> assembleUserStateDtoList(List<UserStateDto> userStates) {
        if ( userStates == null ) {
            return null;
        }

        List<UserState> list = new ArrayList<UserState>( userStates.size() );
        for ( UserStateDto userStateDto : userStates ) {
            list.add( assemble( userStateDto ) );
        }

        return list;
    }
}
