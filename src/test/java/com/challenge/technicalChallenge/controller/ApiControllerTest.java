package com.challenge.technicalChallenge.controller;
import com.challenge.technicalChallenge.dto.CalculateRequest;
import com.challenge.technicalChallenge.dto.CalculateResponse;
import com.challenge.technicalChallenge.repository.CallHistoryRepository;
import com.challenge.technicalChallenge.service.CalculatorService;
import com.challenge.technicalChallenge.service.HistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ApiController.class)
class ApiControllerTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        CalculatorService calculatorService() {
            return Mockito.mock(CalculatorService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CalculatorService calculatorService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private CallHistoryRepository callHistoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCalculateEndpoint() throws Exception {
        CalculateRequest request = new CalculateRequest(new BigDecimal("10"), new BigDecimal("20"));

        when(calculatorService.calculate(request.num1(), request.num2()))
                .thenReturn(new CalculateResponse(
                        new BigDecimal("30"),
                        new BigDecimal("0.15"),
                        new BigDecimal("34.5")
                ));

        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.baseSum").value(30))
                .andExpect(jsonPath("$.total").value(34.5));
    }
}