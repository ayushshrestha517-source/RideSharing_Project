package org.example.ridesharingproject.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProposeFareRequestDto(

        @NotNull(message = "Proposed fare is required")
        @DecimalMin(value = "0.00", inclusive = false,
                message = "Proposed fare must be greater than zero")
        BigDecimal proposedFare

) {
}
