package org.example.ridesharingproject.dao;


import lombok.RequiredArgsConstructor;
import org.example.ridesharingproject.dto.request.EmergencyContactRequestDto;
import org.example.ridesharingproject.dto.request.EmergencyContactStatusRequestDto;
import org.example.ridesharingproject.dto.response.EmergencyContactResponseDto;
import org.example.ridesharingproject.entity.EmergencyContact;
import org.example.ridesharingproject.entity.Passenger;
import org.example.ridesharingproject.exception.AccessDeniedException;
import org.example.ridesharingproject.exception.BadRequestException;
import org.example.ridesharingproject.exception.ResourceNotFoundException;
import org.example.ridesharingproject.mapper.EmergencyContactMapper;
import org.example.ridesharingproject.repository.EmergencyContactRepository;
import org.example.ridesharingproject.repository.PassengerRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmergencyContactDaoImpl implements EmergencyContactDao {

    private final EmergencyContactRepository emergencyContactRepository;
    private final PassengerRepository passengerRepository;
    private final EmergencyContactMapper emergencyContactMapper;

    @Override
    public EmergencyContactResponseDto createEmergencyContact(EmergencyContactRequestDto dto) {
        Passenger passenger = getCurrentPassenger();

        boolean phoneExists = emergencyContactRepository.existsByPassengerIdAndPhoneAndActiveTrue(passenger.getId(), dto.phone());

        if (phoneExists) {
            throw new BadRequestException("This phone number is already added as an emergency contact");
        }

        long activeContacts = emergencyContactRepository.countByPassengerIdAndActiveTrue(passenger.getId());

        if (activeContacts >= 3) {
            throw new BadRequestException("Maximum of 3 active emergency contacts allowed");
        }

        EmergencyContact emergencyContact = emergencyContactMapper.toEntity(dto);

        emergencyContact.setPassenger(passenger);

        EmergencyContact saved = emergencyContactRepository.save(emergencyContact);

        return emergencyContactMapper.toResponseDto(saved);
    }

    @Override
    public List<EmergencyContactResponseDto> getMyEmergencyContacts() {

        Passenger passenger = getCurrentPassenger();

        return passenger.getEmergencyContacts()
                .stream()
                .map(emergencyContactMapper::toResponseDto)
                .toList();
    }

    private Passenger getCurrentPassenger() {
        return passengerRepository
                .findByUserUsername(
                        SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getName()
                )
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));
    }

    @Override
    public EmergencyContactResponseDto changeStatus(Long id, EmergencyContactStatusRequestDto dto) {
        Passenger passenger = getCurrentPassenger();

        EmergencyContact emergencyContact = emergencyContactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Emergency contact not found"));

        if (!emergencyContact.getPassenger().getId().equals(passenger.getId())) {
            throw new AccessDeniedException("You do not have access to this emergency contact");
        }

        if (dto.active() && !emergencyContact.getActive()) {
            long activeContacts = emergencyContactRepository.countByPassengerIdAndActiveTrue(passenger.getId());
            if (activeContacts >= 3) {
                throw new BadRequestException("Maximum of 3 active emergency contacts allowed");
            }
        }

        emergencyContact.setActive(dto.active());

        EmergencyContact updated = emergencyContactRepository.save(emergencyContact);

        return emergencyContactMapper.toResponseDto(updated);
    }

}