package org.example.ridesharingproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ridesharingproject.dao.RideDao;
import org.example.ridesharingproject.dto.request.FareResponseRequestDto;
import org.example.ridesharingproject.dto.request.ProposeFareRequestDto;
import org.example.ridesharingproject.dto.request.RideRequestDto;
import org.example.ridesharingproject.dto.response.ApiResponse;
import org.example.ridesharingproject.dto.response.RideResponseDto;
import org.example.ridesharingproject.enums.RideStatus;
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

    @PreAuthorize("hasRole('DRIVER')")
    @PatchMapping("/propose-fare/{rideId}")
    public ResponseEntity<ApiResponse<RideResponseDto>> proposeFare(@PathVariable Long rideId, @Valid @RequestBody ProposeFareRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.success(rideDao.proposeFare(rideId, dto), "Fare proposed successfully."));
    }

    @PreAuthorize("hasRole('PASSENGER')")
    @PatchMapping("/respond-to-fare/{rideId}")
    public ResponseEntity<ApiResponse<RideResponseDto>> respondToFare(@PathVariable Long rideId, @Valid @RequestBody FareResponseRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.success(rideDao.respondToFare(rideId, dto), dto.accepted() ? "Fare accepted successfully." : "Fare rejected successfully."));
    }

    @PreAuthorize("hasRole('DRIVER')")
    @GetMapping("/my-driver-rides")
    public ResponseEntity<ApiResponse<List<RideResponseDto>>> getMyDriverRides() {
        return ResponseEntity.ok(ApiResponse.success(rideDao.getMyDriverRides(), "Driver rides retrieved successfully."));
    }

    @PreAuthorize("hasRole('PASSENGER')")
    @GetMapping("/my-rides/status")
    public ResponseEntity<ApiResponse<List<RideResponseDto>>> getMyRidesByStatus(@RequestParam RideStatus status) {
        return ResponseEntity.ok(ApiResponse.success(rideDao.getMyRidesByStatus(status),"Passenger rides retrieved successfully."));
    }
}