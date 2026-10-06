package org.example.ridesharingproject.dto.response;

import org.example.ridesharingproject.util.DriverVerificationStatus;

import java.time.LocalDate;

public record DriverResponseDto(

        Long id,
        Long userId,
        String licenseNumber,
        LocalDate licenseExpiryDate,
        String licenseImage,
        DriverVerificationStatus verificationStatus,
        Boolean active,
        Boolean online

) {
}