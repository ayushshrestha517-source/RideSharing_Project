package org.example.ridesharingproject.dto.requestdto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record DriverRequestDto(

        @NotBlank(message = "License number is required")
        @Size(max = 50, message = "License number must not exceed 50 characters")
        String licenseNumber,

        @NotNull(message = "License expiry date is required")
        @Future(message = "License expiry date must be in the future")
        LocalDate licenseExpiryDate

) {
}
