package org.example.ridesharingproject.dto.request;

import org.example.ridesharingproject.enums.UserRole;

public record SelectRoleRequestDto(
        UserRole role
) {
}