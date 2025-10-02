package com.challenge.technicalChallenge.service;
import com.challenge.technicalChallenge.client.PercentageClient;
import com.challenge.technicalChallenge.exception.NoPercentageAvailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PercentageService {

    private final PercentageClient percentageClient;
    private final CacheManager cacheManager;

    /**
     * Obtiene el porcentaje desde el servicio externo y actualiza la caché.
     */
    @CachePut(cacheNames = "percentage", key = "'last'")
    public BigDecimal refreshPercentage() {
        return percentageClient.fetchPercentage();
    }

    /**
     * Intenta obtener el porcentaje del servicio externo.
     * Si falla, usa el último valor de la caché.
     * Si tampoco existe caché, lanza excepción.
     */
    public BigDecimal getPercentageWithFallback() {
        try {
            return refreshPercentage(); // si funciona, se cachea
        } catch (Exception ex) {
            Cache cache = cacheManager.getCache("percentage");
            BigDecimal cached = cache != null ? cache.get("last", BigDecimal.class) : null;
            if (cached == null) {
                throw new NoPercentageAvailableException("No hay porcentaje disponible actualmente");
            }
            return cached;
        }
    }
}