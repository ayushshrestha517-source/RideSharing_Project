package org.example.ridesharingproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ridesharingproject.dao.PassengerDao;
import org.example.ridesharingproject.dto.request.PassengerProfileUpdateRequestDto;
import org.example.ridesharingproject.dto.response.PassengerResponseDto;
import org.example.ridesharingproject.dto.response.ApiResponse;
import org.example.ridesharingproject.entity.Passenger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passenger")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerDao passengerDao;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<ApiResponse<PassengerResponseDto>> getPassengerById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(passengerDao.getPassengerById(id), "Passenger retrieved successfully."));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<PassengerResponseDto>>> getAllPassengers() {
        return ResponseEntity.ok(ApiResponse.success(passengerDao.getAllPassengers(), "Passengers retrieved successfully."));
    }

    @PreAuthorize("hasRole('PASSENGER')")
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<PassengerResponseDto>> getMyProfile() {
        return ResponseEntity.ok(ApiResponse.success(passengerDao.getMyProfile(), "Passenger profile retrieved successfully."));
    }

    @PreAuthorize("hasRole('PASSENGER')")
    @PatchMapping("/update-profile")
    public ResponseEntity<ApiResponse<PassengerResponseDto>> updateMyProfile(@Valid @RequestBody PassengerProfileUpdateRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.success(passengerDao.updateMyProfile(dto), "Passenger profile updated successfully."));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getbyuserid/{userId}")
    public ResponseEntity<ApiResponse<Passenger>> getPassengerByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success(passengerDao.getPassengerByUserId(userId),"Passenger of entered user id "+ userId +" retrieved successfully."));
    }
}