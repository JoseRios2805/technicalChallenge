package com.challenge.technicalChallenge.controller;

import com.challenge.technicalChallenge.dto.CalculateRequest;
import com.challenge.technicalChallenge.dto.CalculateResponse;
import com.challenge.technicalChallenge.model.CallHistory;
import com.challenge.technicalChallenge.repository.CallHistoryRepository;
import com.challenge.technicalChallenge.service.CalculatorService;
import com.challenge.technicalChallenge.service.HistoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final CalculatorService calculatorService;
    private final HistoryService historyService;
    private final CallHistoryRepository callHistoryRepository;

    /**
     * Endpoint principal: recibe dos números, aplica el porcentaje y devuelve el resultado.
     */
    @PostMapping("/calculate")
    public ResponseEntity<CalculateResponse> calculate(
            @RequestBody CalculateRequest request,
            HttpServletRequest http
    ) {
        try {
            // Ejecutar lógica de negocio
            CalculateResponse response = calculatorService.calculate(request.num1(), request.num2());

            // Guardar en historial (asíncrono, no bloquea la respuesta)
            CallHistory history = CallHistory.builder()
                    .timestamp(LocalDateTime.now())
                    .httpMethod(http.getMethod())
                    .endpoint(http.getRequestURI())
                    .params(request.toString())
                    .statusCode(HttpStatus.OK.value())
                    .responseBody(response.toString())
                    .build();

            historyService.saveAsync(history);

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            // En caso de error también lo registramos en el historial
            CallHistory history = CallHistory.builder()
                    .timestamp(LocalDateTime.now())
                    .httpMethod(http.getMethod())
                    .endpoint(http.getRequestURI())
                    .params(request.toString())
                    .statusCode(HttpStatus.SERVICE_UNAVAILABLE.value())
                    .errorMessage(ex.getMessage())
                    .build();

            historyService.saveAsync(history);

            throw ex; // se propagará al GlobalExceptionHandler
        }
    }

    /**
     * Endpoint para consultar el historial de llamadas (paginado).
     */
    @GetMapping("/history")
    public Page<CallHistory> getHistory(Pageable pageable) {
        return callHistoryRepository.findAll(pageable);
    }
}