package com.challenge.technicalChallenge.service;

import com.challenge.technicalChallenge.dto.CalculateResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalculatorServiceTest {

    @Test
    void testCalculate() {
        PercentageService percentageService = mock(PercentageService.class);
        when(percentageService.getPercentageWithFallback()).thenReturn(new BigDecimal("0.15"));

        CalculatorService calculatorService = new CalculatorService(percentageService);

        CalculateResponse response = calculatorService.calculate(
                new BigDecimal("10"),
                new BigDecimal("20")
        );

        assertEquals(new BigDecimal("30"), response.baseSum());
        assertEquals(new BigDecimal("0.15"), response.percentage());
        assertEquals(new BigDecimal("34.50"), response.total());
    }
}