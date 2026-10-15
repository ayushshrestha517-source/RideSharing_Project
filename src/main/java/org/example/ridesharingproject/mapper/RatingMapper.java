package org.example.ridesharingproject.mapper;

import org.example.ridesharingproject.dto.request.RatingRequestDto;
import org.example.ridesharingproject.dto.response.RatingResponseDto;
import org.example.ridesharingproject.entity.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ride", ignore = true)
    @Mapping(target = "passenger", ignore = true)
    @Mapping(target = "driver", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Rating toEntity(RatingRequestDto dto);

    @Mapping(source = "ride.id", target = "rideId")
    @Mapping(source = "passenger.id", target = "passengerId")
    @Mapping(source = "driver.id", target = "driverId")
    RatingResponseDto toResponse(Rating rating);
}
