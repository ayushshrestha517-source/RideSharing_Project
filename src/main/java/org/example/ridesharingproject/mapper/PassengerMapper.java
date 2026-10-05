package org.example.ridesharingproject.mapper;

import org.example.ridesharingproject.dto.responsedto.PassengerResponseDto;
import org.example.ridesharingproject.entity.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PassengerMapper {

    @Mapping(source = "user.id", target = "userId")
    PassengerResponseDto toResponseDto(Passenger passenger);

    List<PassengerResponseDto> toResponseDtoList(List<Passenger> passengers);
}