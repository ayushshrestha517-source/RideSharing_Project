package org.example.ridesharingproject.dao;

import org.example.ridesharingproject.dto.request.EmergencyContactRequestDto;
import org.example.ridesharingproject.dto.request.EmergencyContactStatusRequestDto;
import org.example.ridesharingproject.dto.response.EmergencyContactResponseDto;

import java.util.List;

public interface EmergencyContactDao {
    EmergencyContactResponseDto createEmergencyContact(EmergencyContactRequestDto dto);
    List<EmergencyContactResponseDto> getMyEmergencyContacts();
    EmergencyContactResponseDto changeStatus(Long id, EmergencyContactStatusRequestDto dto);
}
