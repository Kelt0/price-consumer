package com.example.price_consumer;

import com.example.price_consumer.entity.PriceTrackJPA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<PriceTrackJPA, Long> {
}
