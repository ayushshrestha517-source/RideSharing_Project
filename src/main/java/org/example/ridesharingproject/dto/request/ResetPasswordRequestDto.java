package org.example.ridesharingproject.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequestDto(

        @NotBlank
        String email,

        @NotBlank
        String otp,

        @NotBlank
        @Size(min = 8, max = 72)
        String newPassword

) {
}