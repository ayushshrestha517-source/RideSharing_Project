package org.example.ridesharingproject.dto.response;

public record DriverRatingSummaryResponseDto(
        Double averageRating,
        Long totalRatings
) {
}
