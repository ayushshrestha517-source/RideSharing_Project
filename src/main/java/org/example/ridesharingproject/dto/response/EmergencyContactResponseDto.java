package org.example.ridesharingproject.dto.response;

public record EmergencyContactResponseDto(
        Long id,
        String name,
        String phone,
        String relationship,
        Boolean active
) {
}