package com.example.stock.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;

@Entity
public class Stock {
    @Id
    private Long id;

    private Long quantity;

    @Version
    private Long version;

    public Stock() {
    }

    public Stock(Long id, Long quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void decrease(Long quantity) {
        if (this.quantity - quantity < 0) {
            throw new RuntimeException();
        }
        this.quantity -= quantity;
    }
}
