package org.example.ridesharingproject.dto.response;

public record PassengerResponseDto(
        Long id,
        Long userId,
        Boolean active
) {
}