package com.example.price_consumer.service;

import com.example.price_consumer.PriceRepository;
import com.example.price_consumer.entity.PriceTrackJPA;
import jakarta.transaction.Transactional;
import org.gradle.internal.impldep.com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceService {
    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public PriceTrackJPA save(PriceTrackJPA priceTrackJPA) {
        return priceRepository.save(priceTrackJPA);
    }

    public List<PriceTrackJPA> getAllPriceTracks() {
        return priceRepository.findAll();
    }

    public PriceTrackJPA getTaskById(Long id) {
        return priceRepository.getReferenceById(id);
    }

    public void deleteTaskById(Long id) {
        priceRepository.deleteById(id);
    }

    public PriceTrackJPA updateTaskById(Long id, PriceTrackJPA priceTrackJPA) {
        return priceRepository.save(priceTrackJPA);
    }
}
