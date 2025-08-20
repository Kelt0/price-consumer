package com.example.price_consumer;

import org.gradle.internal.impldep.com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceUpdate {

    private double suplied_price;


    public double getPrice() {
        return suplied_price;
    }

    public void setPrice(double suplied_price) {
        this.suplied_price = suplied_price;
    }
}