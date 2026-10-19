package org.example.ridesharingproject.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailOtpRequestDto(
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email
){
}
