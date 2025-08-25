package com.example.price_consumer.repositories;

import com.example.price_consumer.entity.AnalyticalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyticalRepository extends JpaRepository<AnalyticalEntity, Long> {
}
