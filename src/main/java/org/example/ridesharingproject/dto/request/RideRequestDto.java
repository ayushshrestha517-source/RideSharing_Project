package org.example.ridesharingproject.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record RideRequestDto(

        @NotBlank(message = "Pickup address is required")
        @Size(max = 255, message = "Pickup address cannot exceed 255 characters")
        String pickupAddress,

        @NotNull(message = "Pickup latitude is required")
        Double pickupLatitude,

        @NotNull(message = "Pickup longitude is required")
        Double pickupLongitude,

        @NotBlank(message = "Destination address is required")
        @Size(max = 255, message = "Destination address cannot exceed 255 characters")
        String destinationAddress,

        @NotNull(message = "Destination latitude is required")
        Double destinationLatitude,

        @NotNull(message = "Destination longitude is required")
        Double destinationLongitude,

        @NotNull(message = "Requested fare is required")
        @DecimalMin(value = "1.00", message = "Requested fare must be at least 1")
        BigDecimal requestedFare

) {}