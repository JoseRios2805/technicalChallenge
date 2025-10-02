package com.challenge.technicalChallenge.client;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class PercentageClient {

    /**
     * Simulación de una llamada externa.
     * Aquí más adelante usarías WebClient para hacer un GET a un servicio real.
     */
    public BigDecimal fetchPercentage() {
        // Simulamos que el servicio devuelve 15% (0.15)
        return new BigDecimal("0.15");
    }
}
