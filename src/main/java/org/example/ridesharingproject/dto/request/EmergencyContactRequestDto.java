package org.example.ridesharingproject.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmergencyContactRequestDto(

        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must not exceed 100 characters")
        String name,

        @NotBlank(message = "Phone is required")
        @Size(max = 20, message = "Phone must not exceed 20 characters")
        String phone,

        @Size(max = 50, message = "Relationship must not exceed 50 characters")
        String relationship

) {
}