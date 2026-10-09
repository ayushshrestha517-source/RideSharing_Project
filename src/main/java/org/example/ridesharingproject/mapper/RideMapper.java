package org.example.ridesharingproject.mapper;

import org.example.ridesharingproject.dto.request.RideRequestDto;
import org.example.ridesharingproject.dto.response.RideResponseDto;
import org.example.ridesharingproject.entity.Ride;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RideMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passenger", ignore = true)
    @Mapping(target = "driver", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "agreedFare", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "startedAt", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    Ride toEntity(RideRequestDto dto);

    @Mapping(target = "passengerId", source = "passenger.id")
    @Mapping(target = "driverId", source = "driver.id")
    RideResponseDto toResponse(Ride ride);
}