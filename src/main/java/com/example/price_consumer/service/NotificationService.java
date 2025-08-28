package com.example.price_consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {
    private final static Logger LOG = LoggerFactory.getLogger(NotificationService.class);
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${telegram.bot.token}")
    private String botToken;
    @Value("${telegram.chat.id}")
    private String chatId;

    public void sendNotification(String message) {
        String url = String.format("https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s",
                botToken, chatId, message);
        try {
            restTemplate.getForObject(url, String.class);
            LOG.debug("Уведомление отправлено в телеграмм бота");
        }
        catch (Exception e) {
            LOG.error("Не удалось отправить сообщение в телеграмм бота {}", e.getMessage());
        }

    }
}
