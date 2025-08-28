package com.example.price_consumer.service;


import com.example.price_consumer.PriceUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;


@Service
public class PriceService {
   private static final Logger LOG = LoggerFactory.getLogger(PriceService.class);
   private final PriceAnalytics priceAnalytics;
   private final PriceConsumer priceConsumer;

   @Autowired
    public PriceService(PriceAnalytics priceAnalytics, PriceConsumer priceConsumer) {
        this.priceAnalytics = priceAnalytics;
        this.priceConsumer = priceConsumer;
    }

    @KafkaListener(topics = "prices-topic", groupId = "price-group", containerFactory = "kafkaListenerContainerFactory")
    public void trackAndAnalyzePrice(PriceUpdate event, Acknowledgment ack) {
        LOG.info("Запущен модуль-орекстор");
       priceAnalytics.priceAnalysis(event);
       priceConsumer.consumePriceData(event);
       ack.acknowledge();
    }

}
