package org.example.ridesharingproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ridesharingproject.dao.DriverDao;
import org.example.ridesharingproject.dto.request.DriverRequestDto;
import org.example.ridesharingproject.dto.response.ApiResponse;
import org.example.ridesharingproject.dto.response.DriverResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/driver")
@RequiredArgsConstructor
public class DriverController {

    private final DriverDao driverDao;

    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<DriverResponseDto>> applyAsDriver(@Valid @RequestBody DriverRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.success(driverDao.applyAsDriver(dto), "Driver application submitted successfully."));
    }
}