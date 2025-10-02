package com.challenge.technicalChallenge.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CalculateRequest(
        @NotNull BigDecimal num1,
        @NotNull BigDecimal num2
) {}
