package com.ads.entity;

import java.sql.Timestamp;

public class Player {
    private Long id;
    private String username;
    private double budgetCorrente;
    private Timestamp dataCreazione;

    public Player() {}
    public Player(String username, double budgetCorrente) {
        this.username = username;
        this.budgetCorrente = budgetCorrente;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public double getBudgetCorrente() { return budgetCorrente; }
    public void setBudgetCorrente(double budgetCorrente) { this.budgetCorrente = budgetCorrente; }
    public Timestamp getDataCreazione() { return dataCreazione; }
    public void setDataCreazione(Timestamp dataCreazione) { this.dataCreazione = dataCreazione; }
}