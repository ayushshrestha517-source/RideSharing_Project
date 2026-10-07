package org.example.ridesharingproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.ridesharingproject.dao.PassengerDao;
import org.example.ridesharingproject.dto.response.PassengerResponseDto;
import org.example.ridesharingproject.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passenger")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerDao passengerDao;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PassengerResponseDto>> getPassengerById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        passengerDao.getPassengerById(id),
                        "Passenger retrieved successfully."
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PassengerResponseDto>>> getAllPassengers() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        passengerDao.getAllPassengers(),
                        "Passengers retrieved successfully."
                )
        );
    }
}