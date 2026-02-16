package com.ads.db;

import java.sql.Connection;
import java.sql.Statement;
public class DbInitializer {

    public static void initialize() {
        String createPlayerTable = "CREATE TABLE IF NOT EXISTS Player (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "username VARCHAR(50) NOT NULL UNIQUE, " +
                "budget_corrente DOUBLE NOT NULL CHECK (budget_corrente >= 0), " +
                "data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";

        String createStockTable = "CREATE TABLE IF NOT EXISTS Stock (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "ticker VARCHAR(10) UNIQUE NOT NULL, " +
                "nome VARCHAR(100) NOT NULL, " +
                "prezzo_attuale DOUBLE NOT NULL CHECK (prezzo_attuale > 0), " +
                "mu DOUBLE NOT NULL, " +
                "sigma DOUBLE NOT NULL);";

        String createWalletTable = "CREATE TABLE IF NOT EXISTS Wallet (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "player_id BIGINT NOT NULL, " +
                "stock_id BIGINT NOT NULL, " +
                "quantita INT NOT NULL CHECK (quantita > 0), " +
                "prezzo_carico DOUBLE NOT NULL, " +
                "FOREIGN KEY (player_id) REFERENCES Player(id) ON DELETE CASCADE, " +
                "FOREIGN KEY (stock_id) REFERENCES Stock(id), " +
                "CONSTRAINT unique_possesso UNIQUE (player_id, stock_id));";

        String createLastPricesTable = "CREATE TABLE IF NOT EXISTS Last_prices (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "stock_id BIGINT NOT NULL, " +
                "prezzo_registrato DOUBLE NOT NULL, " +
                "data_simulazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (stock_id) REFERENCES Stock(id) ON DELETE CASCADE);";

        String createIndex = "CREATE INDEX IF NOT EXISTS idx_history_stock ON Last_prices(stock_id, data_simulazione);";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(createPlayerTable);
            stmt.execute(createStockTable);
            stmt.execute(createWalletTable);
            stmt.execute(createLastPricesTable);

            try {
                stmt.execute(createIndex);
            } catch (Exception e) {
            }

            System.out.println("Database 'American_dream_simulator' inizializzato correttamente.");

        } catch (Exception e) {
            System.err.println("Errore durante l'inizializzazione del database:");
            e.printStackTrace();
        }
    }
}