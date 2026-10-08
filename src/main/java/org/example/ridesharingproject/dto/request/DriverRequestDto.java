package org.example.ridesharingproject.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.ridesharingproject.util.VehicleType;

import java.time.LocalDate;

public record DriverRequestDto(

        @NotBlank(message = "License number is required")
        @Size(min = 5, max = 30,
                message = "License number must be between 5 and 30 characters")
        String licenseNumber,

        @NotNull(message = "License expiry date is required")
        @Future(message = "License expiry date must be in the future")
        LocalDate licenseExpiryDate,

        @NotBlank(message = "License image is required")
        String licenseImage,

        @NotNull(message = "Vehicle type is required")
        VehicleType vehicleType,

        @NotBlank(message = "Vehicle number is required")
        @Size(min = 3, max = 20,
                message = "Vehicle number must be between 3 and 20 characters")
        String vehicleNumber,

        @NotBlank(message = "Vehicle model is required")
        @Size(min = 2, max = 50,
                message = "Vehicle model must be between 2 and 50 characters")
        String vehicleModel,

        @NotBlank(message = "Vehicle colour is required")
        @Size(min = 2, max = 30,
                message = "Vehicle colour must be between 2 and 30 characters")
        String vehicleColour,

        @NotBlank(message = "Blue book is required")
        String blueBook

) {
}