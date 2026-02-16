package com.ads.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Last_prices")
public class LastPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Column(name = "prezzo_registrato", nullable = false)
    private Double prezzoRegistrato;

    @Column(name = "data_simulazione", updatable = false)
    private LocalDateTime dataSimulazione;

    public LastPrice() {
    }
    public LastPrice(Stock stock, Double prezzoRegistrato) {
        this.stock = stock;
        this.prezzoRegistrato = prezzoRegistrato;
    }

    @PrePersist
    protected void onCreate() {
        if (this.dataSimulazione == null) {
            this.dataSimulazione = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Double getPrezzoRegistrato() {
        return prezzoRegistrato;
    }

    public void setPrezzoRegistrato(Double prezzoRegistrato) {
        this.prezzoRegistrato = prezzoRegistrato;
    }

    public LocalDateTime getDataSimulazione() {
        return dataSimulazione;
    }

    public void setDataSimulazione(LocalDateTime dataSimulazione) {
        this.dataSimulazione = dataSimulazione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LastPrice lastPrice = (LastPrice) o;
        return Objects.equals(id, lastPrice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "LastPrice{" +
                "id=" + id +
                ", prezzoRegistrato=" + prezzoRegistrato +
                ", dataSimulazione=" + dataSimulazione +
                '}';
    }
}