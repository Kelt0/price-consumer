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
<<<<<<< HEAD

    private final AnalyticalRepository analyticalRepository;

    private final AnalyticsState recentPrices = new AnalyticsState(this::performAnalysis);
=======
    private final AnalyticalRepository analyticalRepository;
    private final Queue<Double> recentPrices = new LinkedList<>();
    private static final int MAX_PRICES = 10;
    private final NotificationService notificationService;

    private int messageCount = 0;
>>>>>>> master

    @Autowired
    public PriceAnalytics(AnalyticalRepository analyticalRepository, NotificationService notificationService) {
        this.analyticalRepository = analyticalRepository;
        this.notificationService = notificationService;
    }

    public void priceAnalysis(PriceUpdate suppliedPrice) {
        LOG.info("Анализирую полученные данные из сообщения Kafka: {}", suppliedPrice.getSuppliedPrice());
        recentPrices.add(suppliedPrice.getSuppliedPrice());
<<<<<<< HEAD
=======
        if (recentPrices.size() > MAX_PRICES) {
            recentPrices.poll();
        }
        messageCount++;
        if (messageCount % MAX_PRICES == 0) {
            performAnalysis();
            messageCount = 0;
        }
>>>>>>> master
    }

    private void performAnalysis() {
        if (recentPrices.isEmpty()) {
            return;
        }

        DoubleSummaryStatistics statistics = recentPrices.getStatistics();

        LOG.debug("-----------------------------------");
        LOG.debug("Анализ последних генераций: {}", statistics.getCount());
        LOG.debug("Самая низкая цена: {}", statistics.getMin());
        LOG.debug("Самая высокая цена: {}", statistics.getMax());
        LOG.debug("Средняя цена: {}", statistics.getAverage());
        LOG.debug("-----------------------------------");

        AnalyticalEntity analytical = new AnalyticalEntity(statistics.getMin(), statistics.getMax(), statistics.getAverage(), Instant.now());
        analyticalRepository.save(analytical);

        String message = String.format("Анализ завершен: \n" +
                        "Средняя цена: %.2f\n" +
                        "Макс. цена: %.2f\n" +
                        "Мин. цена: %.2f",
                statistics.getAverage(), statistics.getMax(), statistics.getMin());
        notificationService.sendNotification(message);
    }

    // TODO flush by time? N.K.
    private static class AnalyticsState {
        private static final int MAX_PRICES = 10;

        private final Queue<Double> recentPrices = new LinkedList<>();
        private final Runnable flushTask;

        private int messageCount = 0;

        public AnalyticsState(Runnable flushTask) {
            this.flushTask = flushTask;
        }

        void add(Double price) {
            recentPrices.add(price);
            if (recentPrices.size() > MAX_PRICES) {
                recentPrices.poll();
            }
            messageCount++;

            if (messageCount % MAX_PRICES == 0) {
                flushTask.run();
                messageCount = 0;
            }
        }

        boolean isEmpty() {
            return recentPrices.isEmpty();
        }

        DoubleSummaryStatistics getStatistics() {
            return recentPrices.stream().mapToDouble(Double::doubleValue).summaryStatistics();
        }
    }
}
