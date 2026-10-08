package org.example.ridesharingproject.dto.request;

import jakarta.validation.constraints.NotNull;

public record EmergencyContactStatusRequestDto(
        @NotNull(message = "Active status is required")
        Boolean active
) {
}