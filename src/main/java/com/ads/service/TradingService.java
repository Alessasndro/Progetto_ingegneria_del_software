package com.ads.service;

import com.ads.dao.*;
import com.ads.entity.*;
import java.sql.*;
import java.util.List;

public class TradingService {
    private Connection conn;
    private PlayerDAO playerDAO;
    private WalletDAO walletDAO;
    private StockDAO stockDAO;

    public TradingService(Connection conn) {
        this.conn = conn;
        this.playerDAO = new PlayerDAO(conn);
        this.walletDAO = new WalletDAO(conn);
        this.stockDAO = new StockDAO(conn);
    }

    public boolean eseguiAcquisto(Long pId, Long sId, int qty) throws SQLException {
        try {
            conn.setAutoCommit(false);
            Player p = playerDAO.findById(pId);
            Stock s = stockDAO.findById(sId);

            // Controllo sicurezza: Player o Stock inesistenti
            if (p == null || s == null) {
                System.err.println("Errore: Player o Stock non trovati per l'acquisto.");
                return false;
            }

            double costo = s.getPrezzoAttuale() * qty;

            if (p.getBudgetCorrente() >= costo) {
                playerDAO.updateBudget(pId, p.getBudgetCorrente() - costo);
                walletDAO.save(new Wallet(pId, sId, qty, s.getPrezzoAttuale()));
                conn.commit();
                System.out.println("Acquisto completato con successo!");
                return true;
            }

            System.err.println("Errore: Budget insufficiente.");
            conn.rollback();
            return false;
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public boolean eseguiVendita(Long pId, Long sId, int qty) throws SQLException {
        try {
            conn.setAutoCommit(false);
            Wallet asset = walletDAO.findByPlayerAndStock(pId, sId);
            Stock s = stockDAO.findById(sId);

            // Controllo sicurezza: l'asset nel wallet o lo stock nel DB potrebbero mancare
            if (asset == null || s == null) {
                System.err.println("Errore: Titolo non presente nel portafoglio o stock non trovato.");
                return false;
            }

            if (asset.getQuantita() >= qty) {
                double ricavo = s.getPrezzoAttuale() * qty;
                Player p = playerDAO.findById(pId);

                if (p == null) return false; // Sicurezza estrema

                playerDAO.updateBudget(pId, p.getBudgetCorrente() + ricavo);

                if (asset.getQuantita() == qty) {
                    walletDAO.delete(pId, sId);
                } else {
                    walletDAO.updateQuantita(pId, sId, asset.getQuantita() - qty);
                }

                conn.commit();
                System.out.println("Vendita completata con successo!");
                return true;
            }

            System.err.println("Errore: Quantità insufficiente da vendere.");
            conn.rollback();
            return false;
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void visualizzaPortafoglio(Long pId) throws SQLException {
        List<Wallet> assets = walletDAO.findByPlayerId(pId);
        Player p = playerDAO.findById(pId);

        if (p == null) {
            System.err.println("Errore: Impossibile trovare il giocatore con ID " + pId);
            return;
        }

        System.out.println("\n--- Wallet: " + p.getUsername() + " | Budget: " + String.format("%.2f", p.getBudgetCorrente()) + "€ ---");

        for (Wallet w : assets) {
            Stock s = stockDAO.findById(w.getStockId());
            // Se lo stock è stato cancellato dal DB ma è ancora nel wallet, evitiamo il crash
            if (s != null) {
                double pl = (s.getPrezzoAttuale() - w.getPrezzoCarico()) * w.getQuantita();
                System.out.println(s.getTicker() + " | Qty: " + w.getQuantita() + " | P/L: " + String.format("%.2f", pl) + "€");
            } else {
                System.out.println("ID Stock " + w.getStockId() + " | Qty: " + w.getQuantita() + " (Dati stock non disponibili)");
            }
        }
    }
}