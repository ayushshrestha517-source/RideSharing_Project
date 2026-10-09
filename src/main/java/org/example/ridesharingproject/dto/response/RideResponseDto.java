package org.example.ridesharingproject.dto.response;

import org.example.ridesharingproject.enums.RideStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RideResponseDto(
        Long id,
        Long passengerId,
        Long driverId,
        String pickupAddress,
        Double pickupLatitude,
        Double pickupLongitude,
        String destinationAddress,
        Double destinationLatitude,
        Double destinationLongitude,
        BigDecimal requestedFare,
        BigDecimal agreedFare,
        RideStatus status,
        LocalDateTime createdAt,
        LocalDateTime startedAt,
        LocalDateTime completedAt
) {}