package org.example.ridesharingproject.mapper;

import org.example.ridesharingproject.dto.requestdto.SignupRequestDto;
import org.example.ridesharingproject.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    Users toEntity(SignupRequestDto dto);
}
