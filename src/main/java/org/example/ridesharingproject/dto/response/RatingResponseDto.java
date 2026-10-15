package org.example.ridesharingproject.dto.response;

import java.time.LocalDateTime;

public record RatingResponseDto(
        Long id,
        Long rideId,
        Long passengerId,
        Long driverId,
        Integer score,
        String comment,
        LocalDateTime createdAt
) {
}
