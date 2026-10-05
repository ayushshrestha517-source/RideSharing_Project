package org.example.ridesharingproject.mapper;

import org.example.ridesharingproject.dto.request.SignupRequestDto;
import org.example.ridesharingproject.dto.response.SignupResponseDto;
import org.example.ridesharingproject.entity.Role;
import org.example.ridesharingproject.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    Users toEntity(SignupRequestDto dto);

    @Mapping(
            target = "role",
            expression = "java(getRoleName(user.getRoles()))"
    )
    SignupResponseDto toResponse(Users user);

    default String getRoleName(Set<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return null;
        }

        return roles.iterator().next().getName();
    }
}