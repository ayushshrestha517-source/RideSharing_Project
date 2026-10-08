package org.example.ridesharingproject.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.example.ridesharingproject.enums.Gender;

public record PassengerProfileUpdateRequestDto(

        @Size(max = 50, message = "First name must not exceed 50 characters")
        String firstName,

        @Size(max = 50, message = "Last name must not exceed 50 characters")
        String lastName,

        @Pattern(
                regexp = "^(97|98)\\d{8}$",
                message = "Invalid Nepal phone number"
        )
        String phoneNumber,

        Gender gender,

        String profileImage
) {
}
