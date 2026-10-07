package org.example.ridesharingproject.dao;

import org.example.ridesharingproject.dto.response.PassengerResponseDto;

import java.util.List;

public interface PassengerDao {
    PassengerResponseDto getPassengerById(Long id);
    List<PassengerResponseDto> getAllPassengers();
}
