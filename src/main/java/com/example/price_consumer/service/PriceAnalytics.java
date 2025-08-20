package com.example.price_consumer.service;

import com.example.price_consumer.PriceRepository;
import com.example.price_consumer.PriceUpdate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//создаем аналитику раз в 10 генераций

@Service
public class PriceAnalytics {
    private final PriceRepository priceRepository;

    public PriceAnalytics(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Queue<Double> recentPrices = new LinkedList<>();
    private static final int MAX_PRICES = 10;
    private int messageCount = 0;

    @KafkaListener(topics = "price-topic", groupId = "price-group")
    public void PriceAnalysis(String message) {
        System.out.println("Анализирую полученные данные из сообщения Kafka: " + message);

        try {
            Double price = objectMapper.readTree(message).get("suplied_price").asDouble();
            recentPrices.add(price);
            if (recentPrices.size() > MAX_PRICES) {
                recentPrices.poll();
            }
            messageCount++;
             if (messageCount % MAX_PRICES == 0) {
                 performAnalysis();
             }
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void performAnalysis() {
        if (recentPrices.isEmpty()) {
            return;
        }

        double minPrice = recentPrices.stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
        double maxPrice = recentPrices.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
        double averagePrice = recentPrices.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        System.out.println("-----------------------------------");
        System.out.printf("Анализ последних %d генераций:\n", recentPrices.size());
        System.out.printf("Самая низкая цена: %.2f\n", minPrice);
        System.out.printf("Самая высокая цена: %.2f\n", maxPrice);
        System.out.printf("Средняя цена: %.2f\n", averagePrice);
        System.out.println("-----------------------------------");
    }
}
