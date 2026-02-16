package com.ads.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "Stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "prezzo_attuale")
    private Double lastPrice;
    public Stock() {}
    public Double getLastPrice() {
        return lastPrice;
    }
    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }
}