package org.example.ridesharingproject.dto.response;

import java.math.BigDecimal;

public record DriverEarningsResponseDto(
        BigDecimal totalEarnings,
        long completedRides,
        BigDecimal averageEarningsPerRide
) {
}