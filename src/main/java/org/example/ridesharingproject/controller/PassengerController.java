package org.example.ridesharingproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.ridesharingproject.dao.PassengerDao;
import org.example.ridesharingproject.dto.response.PassengerResponseDto;
import org.example.ridesharingproject.dto.response.ApiResponse;
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
}