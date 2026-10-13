package org.example.ridesharingproject.dto.request;

import jakarta.validation.constraints.NotNull;

public record FareResponseRequestDto(

        @NotNull(message = "Please specify whether you accept the fare")
        Boolean accepted

) {
}
