package org.example.ridesharingproject.dto.response;

import org.example.ridesharingproject.enums.DriverVerificationStatus;
import org.example.ridesharingproject.enums.VehicleType;

import java.time.LocalDate;

public record DriverResponseDto(

        Long id,
        Long userId,

        String licenseNumber,
        LocalDate licenseExpiryDate,
        String licenseImage,

        VehicleType vehicleType,
        String vehicleNumber,
        String vehicleModel,
        String vehicleColour,
        String blueBook,

        DriverVerificationStatus verificationStatus,
        Boolean active,
        Boolean online

) {
}