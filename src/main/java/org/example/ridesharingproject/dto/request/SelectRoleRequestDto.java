package org.example.ridesharingproject.dto.request;

import org.example.ridesharingproject.util.UserRole;

public record SelectRoleRequestDto(
        UserRole role
) {
}