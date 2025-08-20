package com.example.price_consumer.service;

import com.example.price_consumer.PriceRepository;
import com.example.price_consumer.entity.PriceTrackJPA;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PriceConsumer {
    private final PriceRepository priceRepository;

    public PriceConsumer(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @KafkaListener(topics = "price-topic", groupId = "price-group")
    public void consumePriceData(String message) {
        System.out.println("Получено сообщение из Kafka: " + message);

        try {
            JSONObject obj = new JSONObject(message);
            Double price = obj.getDouble("suplied_price");
            PriceTrackJPA priceTrack = new PriceTrackJPA(price, new Date().toInstant());
            priceRepository.save(priceTrack);

            System.out.println("Данные внесены в БД");

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
