package org.example.ridesharingproject.dto.response;

import java.time.LocalDate;

public record DriverResponseDto(
        Long id,
        Long userId,
        String licenseNumber,
        LocalDate licenseExpiryDate,
        Boolean licenseVerified,
        Boolean active,
        Boolean online
) {
}