package org.example.ridesharingproject.dao;

import lombok.AllArgsConstructor;
import org.example.ridesharingproject.dto.response.PassengerResponseDto;
import org.example.ridesharingproject.entity.Passenger;
import org.example.ridesharingproject.repository.PassengerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PassengerDaoImpl implements PassengerDao {

    private final PassengerRepository passengerRepository;

    @Override
    public PassengerResponseDto getPassengerById(Long id) {

        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Passenger not found with id: " + id));

        return new PassengerResponseDto(
                passenger.getId(),
                passenger.getUser().getId(),
                passenger.getActive()
        );
    }

    @Override
    public List<PassengerResponseDto> getAllPassengers() {
        return passengerRepository.findAll()
                .stream()
                .map(passenger -> new PassengerResponseDto(
                        passenger.getId(),
                        passenger.getUser().getId(),
                        passenger.getActive()
                ))
                .toList();
    }
}

