package org.example.ridesharingproject.dto.response;

import java.util.Set;

public record LoginResponseDto(
        String token,
        Set<String> roles
) {
}
