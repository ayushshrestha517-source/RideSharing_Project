package org.example.ridesharingproject.dto.response;

import org.example.ridesharingproject.util.Gender;

public record PassengerResponseDto(
        Long id,
        Long userId,
        String firstName,
        String lastName,
        String username,
        String email,
        String phoneNumber,
        Gender gender,
        String profileImage,
        Boolean active
) {
}