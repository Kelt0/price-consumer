package com.example.price_consumer.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "price_track", schema = "price_db")
public class PriceTrackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_id", nullable = false)
    private Long id;

    @Column(name = "supplied_price", nullable = false, precision = 10, scale = 2)
    private Double suppliedPrice;

    @Column(name = "track_time", nullable = false)
    private Instant trackTime;

    public PriceTrackEntity(Double suppliedPrice, Instant trackTime) {
        this.suppliedPrice = suppliedPrice;
        this.trackTime = trackTime;
    }

    public PriceTrackEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSuppliedPrice() {
        return suppliedPrice;
    }

    public void setSuppliedPrice(Double suppliedPrice) {
        this.suppliedPrice = suppliedPrice;
    }

    public Instant getTrackTime() {
        return trackTime;
    }

    public void setTrackTime(Instant trackTime) {
        this.trackTime = trackTime;
    }

}