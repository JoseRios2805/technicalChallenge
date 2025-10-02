package com.challenge.technicalChallenge.service;

import com.challenge.technicalChallenge.dto.CalculateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CalculatorService {

    private final PercentageService percentageService;

    /**
     * Suma num1 y num2, obtiene el porcentaje y devuelve el resultado.
     */
    public CalculateResponse calculate(BigDecimal num1, BigDecimal num2) {
        BigDecimal base = num1.add(num2);
        BigDecimal percentage = percentageService.getPercentageWithFallback();
        BigDecimal total = base.add(base.multiply(percentage));

        return new CalculateResponse(base, percentage, total);
    }
}