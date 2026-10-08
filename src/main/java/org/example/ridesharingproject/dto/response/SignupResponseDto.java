package org.example.ridesharingproject.dto.response;

public record SignupResponseDto(
        Long id,
        String firstName,
        String lastName,
        String username,
        String email,
        String phoneNumber,
        String gender,
        String profileImage,
        String role
) {
}

