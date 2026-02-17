package com.ads;

import com.ads.service.TradingService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/American_dream_simulator";
    private static final String USER = "root";
    private static final String PASS = "1234";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            System.out.println("Connessione riuscita!");

            inizializzaDatabase(conn);

            TradingService trading = new TradingService(conn);

            System.out.println("\n--- TEST OPERAZIONI ---");
            boolean okAcquisto = trading.eseguiAcquisto(1L, 1L, 5);

            if (okAcquisto) {
                System.out.println("Esito: Acquisto completato!");
                trading.visualizzaPortafoglio(1L);
            } else {
                System.out.println("Esito: Acquisto fallito (Dati non trovati o budget insufficiente).");
            }

        } catch (SQLException e) {
            System.err.println("Errore nel Main: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void inizializzaDatabase(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stmt.executeUpdate("DROP TABLE IF EXISTS Wallet");
            stmt.executeUpdate("DROP TABLE IF EXISTS Player");
            stmt.executeUpdate("DROP TABLE IF EXISTS Stock");
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");

            stmt.executeUpdate("CREATE TABLE Player (" +
                    "id BIGINT PRIMARY KEY, " +
                    "username VARCHAR(255), " +
                    "budget_corrente DOUBLE, " + // Da PlayerDAO.java
                    "data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

            stmt.executeUpdate("CREATE TABLE Stock (" +
                    "id BIGINT PRIMARY KEY, " +
                    "ticker VARCHAR(10), " +
                    "nome_societa VARCHAR(255), " + // Da StockDAO.java
                    "prezzo_attuale DOUBLE)");

            stmt.executeUpdate("CREATE TABLE Wallet (" +
                    "player_id BIGINT, " +
                    "stock_id BIGINT, " +
                    "quantita INT, " +
                    "prezzo_carico DOUBLE, " +
                    "PRIMARY KEY (player_id, stock_id))");

            stmt.executeUpdate("INSERT INTO Player (id, username, budget_corrente) VALUES (1, 'Pippo', 10000.0)");
            stmt.executeUpdate("INSERT INTO Stock (id, ticker, nome_societa, prezzo_attuale) VALUES (1, 'AAPL', 'Apple Inc.', 180.50)");

            System.out.println("✅ Database resettato e popolato con Pippo (ID:1) e Apple (ID:1)");
        }
    }
}