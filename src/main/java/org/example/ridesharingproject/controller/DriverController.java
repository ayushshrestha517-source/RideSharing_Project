package org.example.ridesharingproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ridesharingproject.dao.DriverDao;
import org.example.ridesharingproject.dto.request.DriverRequestDto;
import org.example.ridesharingproject.dto.response.ApiResponse;
import org.example.ridesharingproject.dto.response.DriverResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
public class DriverController {

    private final DriverDao driverDao;

    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<DriverResponseDto>> applyAsDriver(@Valid @RequestBody DriverRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.success(driverDao.applyAsDriver(dto), "Driver application submitted successfully."));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/approve/{driverId}")
    public ResponseEntity<ApiResponse<DriverResponseDto>> approveDriver(@PathVariable Long driverId) {
        return ResponseEntity.ok(ApiResponse.success(driverDao.approveDriver(driverId), "Driver approved successfully."));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/reject/{driverId}")
    public ResponseEntity<ApiResponse<DriverResponseDto>> rejectDriver(@PathVariable Long driverId) {
        return ResponseEntity.ok(ApiResponse.success(driverDao.rejectDriver(driverId), "Driver rejected successfully."));
    }

    @PutMapping("/resubmit")
    public ResponseEntity<ApiResponse<DriverResponseDto>> resubmitDriver(@Valid @RequestBody DriverRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.success(driverDao.resubmitDriver(dto), "Driver application resubmitted successfully."));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/pending")
    public ResponseEntity<ApiResponse<List<DriverResponseDto>>> getPendingDrivers() {
        return ResponseEntity.ok(ApiResponse.success(driverDao.getPendingDrivers(), "Pending driver applications retrieved successfully."));
    }

    @PutMapping("/go-online")
    public ResponseEntity<ApiResponse<DriverResponseDto>> goOnline() {
        return ResponseEntity.ok(ApiResponse.success(driverDao.goOnline(), "Driver is now online."));
    }

    @PutMapping("/go-offline")
    public ResponseEntity<ApiResponse<DriverResponseDto>> goOffline() {
        return ResponseEntity.ok(ApiResponse.success(driverDao.goOffline(), "Driver is now offline."));
    }

    @GetMapping("/approved/{driverId}")
    public ResponseEntity<ApiResponse<DriverResponseDto>> getApprovedDriverById(@PathVariable Long driverId) {
        return ResponseEntity.ok(ApiResponse.success(driverDao.getApprovedDriverById(driverId), "Approved driver retrieved successfully."));
    }

    @GetMapping("/approved")
    public ResponseEntity<ApiResponse<List<DriverResponseDto>>> getApprovedDrivers() {
        return ResponseEntity.ok(ApiResponse.success(driverDao.getApprovedDrivers(), "Approved drivers retrieved successfully."));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/remove/{driverId}")
    public ResponseEntity<ApiResponse<DriverResponseDto>> removeDriver(@PathVariable Long driverId) {
        return ResponseEntity.ok(ApiResponse.success(driverDao.removeDriver(driverId), "Driver removed successfully."));
    }
}