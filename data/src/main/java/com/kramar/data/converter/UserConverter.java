package com.kramar.data.converter;

import com.kramar.data.dbo.UserDbo;
import com.kramar.data.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserConverter extends AbstractDtoConverter<UserDbo, UserDto> {

    @Override
    protected UserDto createNewDto() {
        return new UserDto();
    }

    @Override
    protected UserDbo createNewEntity() {
        return new UserDbo();
    }

    @Override
    protected void convertFromEntity(final UserDbo entity, final UserDto dto) {
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setUserName(entity.getUserName());
        dto.setUserSurname(entity.getUserSurname());
        dto.setStatus(entity.getStatus());
        dto.setUserRoles(entity.getUserRoles());
    }

    @Override
    protected void convertFromDto(final UserDto dto, final UserDbo entity) {
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setUserName(dto.getUserName());
        entity.setUserSurname(dto.getUserSurname());
        entity.setStatus(dto.getStatus());
        entity.setUserRoles(dto.getUserRoles());
    }
}
