package com.example.price_consumer.repositories;

import com.example.price_consumer.entity.PriceTrackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<PriceTrackEntity, Long> {
}
