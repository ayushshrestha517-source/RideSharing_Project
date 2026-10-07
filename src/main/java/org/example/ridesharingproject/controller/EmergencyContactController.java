package org.example.ridesharingproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ridesharingproject.dao.EmergencyContactDao;
import org.example.ridesharingproject.dto.request.EmergencyContactRequestDto;
import org.example.ridesharingproject.dto.request.EmergencyContactStatusRequestDto;
import org.example.ridesharingproject.dto.response.EmergencyContactResponseDto;
import org.example.ridesharingproject.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emergency-contacts")
@RequiredArgsConstructor
public class EmergencyContactController {

    private final EmergencyContactDao emergencyContactDao;
    @PreAuthorize("hasRole('PASSENGER')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<EmergencyContactResponseDto>> createEmergencyContact(@Valid @RequestBody EmergencyContactRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.success(emergencyContactDao.createEmergencyContact(dto),"Emergency contact assigned successfully"));
    }

    @PreAuthorize("hasRole('PASSENGER')")
    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<EmergencyContactResponseDto>>> getMyEmergencyContacts() {
        return ResponseEntity.ok(ApiResponse.success(emergencyContactDao.getMyEmergencyContacts(), "Emergency contacts retrieved successfully."));
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<ApiResponse<EmergencyContactResponseDto>> changeStatus(@PathVariable Long id, @Valid @RequestBody EmergencyContactStatusRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.success(emergencyContactDao.changeStatus(id, dto), "Emergency contact status updated successfully."));
    }

}