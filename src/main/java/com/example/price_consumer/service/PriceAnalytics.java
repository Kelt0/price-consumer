package com.example.price_consumer.service;
import com.example.price_consumer.entity.AnalyticalEntity;
import com.example.price_consumer.repositories.AnalyticalRepository;
import com.example.price_consumer.PriceUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.DoubleSummaryStatistics;
import java.util.LinkedList;
import java.util.Queue;

//создаем аналитику раз в 10 генераций

@Service
public class PriceAnalytics {
    private static final Logger log = LoggerFactory.getLogger(PriceAnalytics.class);
    private final AnalyticalRepository analyticalRepository;

    private final Queue<Double> recentPrices = new LinkedList<>();
    private static final int MAX_PRICES = 10;
    private int messageCount = 0;

    @Autowired
    public PriceAnalytics(AnalyticalRepository analyticalRepository) {
        this.analyticalRepository = analyticalRepository;
    }

    public void priceAnalysis(PriceUpdate suppliedPrice) {
        log.info("Анализирую полученные данные из сообщения Kafka: {}", suppliedPrice.getSuppliedPrice());
            recentPrices.add(suppliedPrice.getSuppliedPrice());
            if (recentPrices.size() > MAX_PRICES) {
                recentPrices.poll();
            }
            messageCount++;
             if (messageCount % MAX_PRICES == 0) {
                 performAnalysis();
                 messageCount = 0;
             }
    }

    private void performAnalysis() {
        if (recentPrices.isEmpty()) {
            return;
        }

        DoubleSummaryStatistics statistics = recentPrices.stream().mapToDouble(Double::doubleValue).summaryStatistics();

        log.info("-----------------------------------");
        log.info("Анализ последних  генераций: {}", statistics.getCount());
        log.info("Самая низкая цена: {}", statistics.getMin());
        log.info("Самая высокая цена: {}", statistics.getMax());
        log.info("Средняя цена: {}", statistics.getAverage());
        log.info("-----------------------------------");

        AnalyticalEntity analytical = new AnalyticalEntity(statistics.getMin(), statistics.getMax(), statistics.getAverage(), Instant.now());
        analyticalRepository.save(analytical);
        log.info("Внесены данные анализа в БД");
    }
}
