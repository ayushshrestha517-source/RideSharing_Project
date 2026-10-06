package org.example.ridesharingproject.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record DriverRequestDto(

        @NotBlank(message = "License number is required")
        @Size(min = 5, max = 30, message = "License number must be between 5 and 30 characters")
        String licenseNumber,

        @NotNull(message = "License expiry date is required")
        @Future(message = "License expiry date must be in the future")
        LocalDate licenseExpiryDate,

        @NotBlank(message = "License image is required")
        String licenseImage

) {
}