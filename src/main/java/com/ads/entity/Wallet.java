package com.ads.entity;

public class Wallet {
    private Long id;
    private Long playerId;
    private Long stockId;
    private int quantita;
    private double prezzoCarico;

    public Wallet() {}

    public Wallet(Long playerId, Long stockId, int quantita, double prezzoCarico) {
        this.playerId = playerId;
        this.stockId = stockId;
        this.quantita = quantita;
        this.prezzoCarico = prezzoCarico;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPlayerId() { return playerId; }
    public void setPlayerId(Long playerId) { this.playerId = playerId; }

    public Long getStockId() { return stockId; }
    public void setStockId(Long stockId) { this.stockId = stockId; }

    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }

    public double getPrezzoCarico() { return prezzoCarico; }
    public void setPrezzoCarico(double prezzoCarico) { this.prezzoCarico = prezzoCarico; }

    public double getValoreInvestito() {
        return this.quantita * this.prezzoCarico;
    }
}