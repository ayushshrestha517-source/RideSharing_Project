package org.example.ridesharingproject.mapper;

import org.example.ridesharingproject.dto.request.EmergencyContactRequestDto;
import org.example.ridesharingproject.dto.response.EmergencyContactResponseDto;
import org.example.ridesharingproject.entity.EmergencyContact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmergencyContactMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passenger", ignore = true)
    @Mapping(target = "active", ignore = true)
    EmergencyContact toEntity(EmergencyContactRequestDto dto);

    EmergencyContactResponseDto toResponseDto(EmergencyContact entity);
}