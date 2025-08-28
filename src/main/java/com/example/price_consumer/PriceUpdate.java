package com.example.price_consumer;

public class PriceUpdate {
    private double suppliedPrice;

    public double getSuppliedPrice() {
        return suppliedPrice;
    }

    public void setSuppliedPrice(double suppliedPrice) {
        this.suppliedPrice = suppliedPrice;
    }

    public PriceUpdate(double suppliedPrice) {
        this.suppliedPrice = suppliedPrice;
    }

    @Override
    public String toString() {
        return "{" +
                "suppliedPrice=" + suppliedPrice +
                '}';
    }

    public PriceUpdate() {
    }
}

