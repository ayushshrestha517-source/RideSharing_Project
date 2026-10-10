package org.example.ridesharingproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ridesharingproject.dao.RideDao;
import org.example.ridesharingproject.dto.request.RideRequestDto;
import org.example.ridesharingproject.dto.response.ApiResponse;
import org.example.ridesharingproject.dto.response.RideResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideDao rideDao;

    @PreAuthorize("hasRole('PASSENGER')")
    @PostMapping("/request")
    public ResponseEntity<ApiResponse<RideResponseDto>> requestRide(@Valid @RequestBody RideRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.success(rideDao.requestRide(dto),"Ride requested successfully."));
    }

    @PreAuthorize("hasRole('DRIVER')")
    @GetMapping("/get-requested")
    public ResponseEntity<ApiResponse<List<RideResponseDto>>> getRequestedRides(){
        return ResponseEntity.ok(ApiResponse.success(rideDao.getRequestedRides(),"Available Rides retrieved successfully."));
    }
}

