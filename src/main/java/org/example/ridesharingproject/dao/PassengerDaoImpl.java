package org.example.ridesharingproject.dao;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.ridesharingproject.dto.request.PassengerProfileUpdateRequestDto;
import org.example.ridesharingproject.dto.response.PassengerResponseDto;
import org.example.ridesharingproject.entity.Passenger;
import org.example.ridesharingproject.exception.ResourceNotFoundException;
import org.example.ridesharingproject.repository.PassengerRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PassengerDaoImpl implements PassengerDao {

    private final PassengerRepository passengerRepository;

    @Override
    public PassengerResponseDto getPassengerById(Long id) {

        Passenger passenger = passengerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Passenger not found with id: " + id));

        return new PassengerResponseDto(
                passenger.getId(),
                passenger.getUser().getId(),
                passenger.getUser().getFirstName(),
                passenger.getUser().getLastName(),
                passenger.getUser().getUsername(),
                passenger.getUser().getEmail(),
                passenger.getUser().getPhoneNumber(),
                passenger.getUser().getGender(),
                passenger.getUser().getProfileImage(),
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
                        passenger.getUser().getFirstName(),
                        passenger.getUser().getLastName(),
                        passenger.getUser().getUsername(),
                        passenger.getUser().getEmail(),
                        passenger.getUser().getPhoneNumber(),
                        passenger.getUser().getGender(),
                        passenger.getUser().getProfileImage(),
                        passenger.getActive()
                ))
                .toList();
    }

    @Override
    public PassengerResponseDto getMyProfile() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Passenger passenger = passengerRepository.findByUserUsername(username).orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));

        return new PassengerResponseDto(
                passenger.getId(),
                passenger.getUser().getId(),
                passenger.getUser().getFirstName(),
                passenger.getUser().getLastName(),
                passenger.getUser().getUsername(),
                passenger.getUser().getEmail(),
                passenger.getUser().getPhoneNumber(),
                passenger.getUser().getGender(),
                passenger.getUser().getProfileImage(),
                passenger.getActive()
        );
    }

    @Transactional
    @Override
    public PassengerResponseDto updateMyProfile(PassengerProfileUpdateRequestDto dto) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Passenger passenger = passengerRepository.findByUserUsername(username).orElseThrow(() -> new RuntimeException("Passenger not found"));

        if (dto.firstName() != null) {
            passenger.getUser().setFirstName(dto.firstName());
        }

        if (dto.lastName() != null) {
            passenger.getUser().setLastName(dto.lastName());
        }

        if (dto.phoneNumber() != null) {
            passenger.getUser().setPhoneNumber(dto.phoneNumber());
        }

        if (dto.gender() != null) {
            passenger.getUser().setGender(dto.gender());
        }

        if (dto.profileImage() != null) {
            passenger.getUser().setProfileImage(dto.profileImage());
        }

        passengerRepository.save(passenger);

        return new PassengerResponseDto(
                passenger.getId(),
                passenger.getUser().getId(),
                passenger.getUser().getFirstName(),
                passenger.getUser().getLastName(),
                passenger.getUser().getUsername(),
                passenger.getUser().getEmail(),
                passenger.getUser().getPhoneNumber(),
                passenger.getUser().getGender(),
                passenger.getUser().getProfileImage(),
                passenger.getActive()
        );
    }
}

