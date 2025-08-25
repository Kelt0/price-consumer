package com.example.price_consumer.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "analytical_table", schema = "price_db")
public class AnalyticalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "analytical_id", nullable = false)
    private Long id;

    @Column(name = "min", nullable = false, precision = 10, scale = 2)
    private double min;

    @Column(name = "max", nullable = false, precision = 10, scale = 2)
    private double max;

    @Column(name = "average", nullable = false, precision = 10, scale = 2)
    private double average;

    @Column(name = "analytical_time", nullable = false)
    private Instant analyticalTime;

    public AnalyticalEntity(double min, double max, double average, Instant analyticalTime) {
        this.min = min;
        this.max = max;
        this.average = average;
        this.analyticalTime = analyticalTime;
    }

    public AnalyticalEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public Instant getAnalyticalTime() {
        return analyticalTime;
    }

    public void setAnalyticalTime(Instant analyticalTime) {
        this.analyticalTime = analyticalTime;
    }

}