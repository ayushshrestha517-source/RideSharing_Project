package org.example.ridesharingproject.dao;

import org.example.ridesharingproject.dto.request.PassengerProfileUpdateRequestDto;
import org.example.ridesharingproject.dto.response.PassengerResponseDto;
import org.example.ridesharingproject.entity.Passenger;

import java.util.List;

public interface PassengerDao {
    PassengerResponseDto getPassengerById(Long id);
    List<PassengerResponseDto> getAllPassengers();
    PassengerResponseDto getMyProfile();
    PassengerResponseDto updateMyProfile(PassengerProfileUpdateRequestDto dto);
    Passenger getPassengerByUserId(Long userId);
}
