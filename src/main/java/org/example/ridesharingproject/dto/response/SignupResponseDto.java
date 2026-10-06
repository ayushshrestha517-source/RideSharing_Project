package org.example.ridesharingproject.dto.response;
import org.example.ridesharingproject.entity.Role;
import org.example.ridesharingproject.util.Gender;

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

