package com.ads.entity;

public class Stock {
    private Long id;
    private String ticker;
    private String nomeSocieta;
    private double prezzoAttuale;

    public Stock() {}

    public Stock(Long id, String ticker, String nomeSocieta, double prezzoAttuale) {
        this.id = id;
        this.ticker = ticker;
        this.nomeSocieta = nomeSocieta;
        this.prezzoAttuale = prezzoAttuale;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTicker() { return ticker; }
    public void setTicker(String ticker) { this.ticker = ticker; }

    public String getNomeSocieta() { return nomeSocieta; }
    public void setNomeSocieta(String nomeSocieta) { this.nomeSocieta = nomeSocieta; }

    public double getPrezzoAttuale() { return prezzoAttuale; }
    public void setPrezzoAttuale(double prezzoAttuale) { this.prezzoAttuale = prezzoAttuale; }
}