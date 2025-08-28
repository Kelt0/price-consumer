package com.example.price_consumer.service;


import com.example.price_consumer.PriceUpdate;
import com.example.price_consumer.entity.PriceTrackEntity;
import com.example.price_consumer.repositories.PriceRepository;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PriceService {
    private static final Logger log = LoggerFactory.getLogger(PriceService.class);
    private final PriceAnalytics priceAnalytics;
    private final PriceRepository priceRepository;

    @Autowired
    public PriceService(PriceAnalytics priceAnalytics, PriceRepository priceRepository) {
        this.priceAnalytics = priceAnalytics;
        this.priceRepository = priceRepository;
    }

    @Transactional
    public void trackAndAnalyzePrice(PriceUpdate event) {
        log.info("Запущен модуль-орекстор");

        priceRepository.save(new PriceTrackEntity(event.getSuppliedPrice(), Instant.now()));
        priceAnalytics.priceAnalysis(event);
    }

}
