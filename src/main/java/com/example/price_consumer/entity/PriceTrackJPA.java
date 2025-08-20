package com.example.price_consumer.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "price_track", schema = "price_db")
public class PriceTrackJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_id", nullable = false)
    private Long id;

    @Column(name = "suplied_price", nullable = false)
    private Double supliedPrice;

    @Column(name = "track_time", nullable = false)
    private Instant trackTime;

    public PriceTrackJPA(Double supliedPrice, Instant trackTime) {
        this.supliedPrice = supliedPrice;
        this.trackTime = trackTime;
    }

    public PriceTrackJPA() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSupliedPrice() {
        return supliedPrice;
    }

    public void setSupliedPrice(Double supliedPrice) {
        this.supliedPrice = supliedPrice;
    }

    public Instant getTrackTime() {
        return trackTime;
    }

    public void setTrackTime(Instant trackTime) {
        this.trackTime = trackTime;
    }

}