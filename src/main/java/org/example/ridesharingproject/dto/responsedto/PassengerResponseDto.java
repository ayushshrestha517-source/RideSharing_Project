package org.example.ridesharingproject.dto.responsedto;

public record PassengerResponseDto(
        Long id,
        Long userId,
        Boolean active
) {
}