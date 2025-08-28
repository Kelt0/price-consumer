package com.example.price_consumer.service;

import com.example.price_consumer.PriceUpdate;
import com.example.price_consumer.entity.PriceTrackEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PriceConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(PriceConsumer.class);

    private final PriceService priceService;

    @Autowired
    public PriceConsumer(PriceService priceService) {
        this.priceService = priceService;
    }

    // TODO Manual ACK for Kafka
    @KafkaListener(topics = "prices-topic", groupId = "price-group", containerFactory = "kafkaListenerContainerFactory")
    public void consumePriceData(PriceUpdate suppliedPrice) {

        LOG.info("Получено сообщение из Kafka {}", suppliedPrice.getSuppliedPrice());
        priceService.trackAndAnalyzePrice(suppliedPrice);
        LOG.info("Данные внесены в БД.");
    }
}
