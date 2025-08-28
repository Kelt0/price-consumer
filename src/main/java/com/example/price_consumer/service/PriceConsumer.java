package com.example.price_consumer.service;

import com.example.price_consumer.repositories.PriceRepository;
import com.example.price_consumer.PriceUpdate;
import com.example.price_consumer.entity.PriceTrackEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PriceConsumer {
    private final PriceRepository priceRepository;
    private static final Logger LOG = LoggerFactory.getLogger(PriceConsumer.class);

    @Autowired
    public PriceConsumer(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public void consumePriceData(PriceUpdate suppliedPrice) {
        LOG.info("Получено сообщение из Kafka {}", suppliedPrice.getSuppliedPrice());
        PriceTrackEntity priceTrack = new PriceTrackEntity(suppliedPrice.getSuppliedPrice(), Instant.now());
        priceRepository.save(priceTrack);
        LOG.info("Данные внесены в БД.");
    }
}
