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
    private static final Logger LOG = LoggerFactory.getLogger(PriceAnalytics.class);
    private final AnalyticalRepository analyticalRepository;
    private final Queue<Double> recentPrices = new LinkedList<>();
    private static final int MAX_PRICES = 10;
    private final NotificationService notificationService;

    private int messageCount = 0;

    @Autowired
    public PriceAnalytics(AnalyticalRepository analyticalRepository, NotificationService notificationService) {
        this.analyticalRepository = analyticalRepository;
        this.notificationService = notificationService;
    }

    public void priceAnalysis(PriceUpdate suppliedPrice) {
        LOG.info("Анализирую полученные данные из сообщения Kafka: {}", suppliedPrice.getSuppliedPrice());
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

        LOG.debug("-----------------------------------");
        LOG.debug("Анализ последних  генераций: {}", statistics.getCount());
        LOG.debug("Самая низкая цена: {}", statistics.getMin());
        LOG.debug("Самая высокая цена: {}", statistics.getMax());
        LOG.debug("Средняя цена: {}", statistics.getAverage());
        LOG.debug("-----------------------------------");

        AnalyticalEntity analytical = new AnalyticalEntity( statistics.getMin(), statistics.getMax(), statistics.getAverage(), Instant.now());
        analyticalRepository.save(analytical);
        String message = String.format("Анализ завершен: \n" +
                        "Средняя цена: %.2f\n" +
                        "Макс. цена: %.2f\n" +
                        "Мин. цена: %.2f",
                statistics.getAverage(), statistics.getMax(), statistics.getMin());
        notificationService.sendNotification(message);
    }
}
