package org.example.ridesharingproject.mapper;

import org.example.ridesharingproject.dto.requestdto.DriverRequestDto;
import org.example.ridesharingproject.dto.responsedto.DriverResponseDto;
import org.example.ridesharingproject.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DriverMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "licenseVerified", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "online", ignore = true)
    Driver toEntity(DriverRequestDto dto);

    @Mapping(source = "user.id", target = "userId")
    DriverResponseDto toResponseDto(Driver driver);
}
