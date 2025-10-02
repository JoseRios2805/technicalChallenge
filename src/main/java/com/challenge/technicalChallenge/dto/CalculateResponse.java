package com.challenge.technicalChallenge.dto;
import java.math.BigDecimal;

public record CalculateResponse(
        BigDecimal baseSum,
        BigDecimal percentage,
        BigDecimal total
) {}