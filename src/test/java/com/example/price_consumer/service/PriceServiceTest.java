package com.example.price_consumer.service;

import com.example.price_consumer.PriceUpdate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.support.Acknowledgment;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PriceServiceTest {
    @Mock
    private PriceAnalytics priceAnalytics;
    @Mock
    private PriceConsumer priceConsumer;
    @Mock
    private Acknowledgment acknowledgment;

    @InjectMocks
    private PriceService priceService;



    @Test
    void testPriceService() {
        PriceUpdate mockPriceUpdate = new PriceUpdate(11.5);

        priceService.trackAndAnalyzePrice(mockPriceUpdate, acknowledgment);

        verify(priceConsumer).consumePriceData(mockPriceUpdate);
        verify(priceAnalytics).priceAnalysis(mockPriceUpdate);
        verify(acknowledgment, times(1)).acknowledge();
    }
}
