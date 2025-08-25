package com.example.price_consumer.service;

import com.example.price_consumer.PriceUpdate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PriceServiceTest {
    @Mock
    private PriceAnalytics priceAnalytics;
    @Mock
    private PriceConsumer priceConsumer;

    @InjectMocks
    private PriceService priceService;

    @Test
    void testPriceService() {
        PriceUpdate mockPriceUpdate = new PriceUpdate(11.5);

        priceService.trackAndAnalyzePrice(mockPriceUpdate);

        verify(priceConsumer).consumePriceData(mockPriceUpdate);
        verify(priceAnalytics).priceAnalysis(mockPriceUpdate);
    }
}
