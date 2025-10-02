package com.challenge.technicalChallenge.service;

import com.challenge.technicalChallenge.client.PercentageClient;
import com.challenge.technicalChallenge.exception.NoPercentageAvailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PercentageServiceTest {

    private PercentageClient client;
    private CacheManager cacheManager;
    private PercentageService service;

    @BeforeEach
    void setUp() {
        client = mock(PercentageClient.class);
        cacheManager = new ConcurrentMapCacheManager("percentage");
        service = new PercentageService(client, cacheManager);
    }

    @Test
    void testSuccess() {
        when(client.fetchPercentage()).thenReturn(new BigDecimal("0.10"));
        assertEquals(new BigDecimal("0.10"), service.getPercentageWithFallback());
    }

    @Test
    void testFallback() {
        when(client.fetchPercentage()).thenReturn(new BigDecimal("0.20"));
        service.getPercentageWithFallback();

        when(client.fetchPercentage()).thenThrow(new RuntimeException("Service down"));

        BigDecimal result = service.getPercentageWithFallback();
        assertEquals(new BigDecimal("0.20"), result);
    }

    @Test
    void testNoCacheAndFailure() {
        when(client.fetchPercentage()).thenThrow(new RuntimeException("Service down"));

        assertThrows(NoPercentageAvailableException.class, () -> service.getPercentageWithFallback());
    }
}