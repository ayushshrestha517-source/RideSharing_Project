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

    @PreAuthorize("hasRole('DRIVER')")
    @PatchMapping("/accept/{rideId}")
    public ResponseEntity<ApiResponse<RideResponseDto>> acceptRide(@PathVariable Long rideId) {
        return ResponseEntity.ok(ApiResponse.success(rideDao.acceptRide(rideId),"Ride accepted successfully."));
    }

    @PreAuthorize("hasRole('PASSENGER')")
    @PatchMapping("/cancel/{rideId}")
    public ResponseEntity<ApiResponse<RideResponseDto>> cancelRide(@PathVariable Long rideId) {
        return ResponseEntity.ok(ApiResponse.success(rideDao.cancelRide(rideId),"Ride cancelled successfully."));
    }

    @PreAuthorize("hasRole('PASSENGER')")
    @GetMapping("/my-rides")
    public ResponseEntity<ApiResponse<List<RideResponseDto>>> getMyRides() {
        return ResponseEntity.ok(ApiResponse.success(rideDao.getMyRides(), "Your rides retrieved successfully."));
    }

    @PreAuthorize("hasRole('DRIVER')")
    @PatchMapping("/start/{rideId}")
    public ResponseEntity<ApiResponse<RideResponseDto>> startRide(@PathVariable Long rideId) {
        return ResponseEntity.ok(ApiResponse.success(rideDao.startRide(rideId), "Ride started successfully."));
    }

    @PreAuthorize("hasRole('DRIVER')")
    @PatchMapping("/complete/{rideId}")
    public ResponseEntity<ApiResponse<RideResponseDto>> completeRide(@PathVariable Long rideId) {
        return ResponseEntity.ok(ApiResponse.success(rideDao.completeRide(rideId), "Ride completed successfully."));
    }

}