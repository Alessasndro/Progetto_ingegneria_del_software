package com.ads.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LastPricesDAO {
    private Connection connection;

    public LastPricesDAO(Connection connection) {
        this.connection = connection;
    }

    public void registraPrezzo(Long stockId, double prezzo) throws SQLException {
        String sql = "INSERT INTO Last_prices (stock_id, prezzo_registrato) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, stockId);
            ps.setDouble(2, prezzo);
            ps.executeUpdate();
        }
    }

    public List<Double> getStoricoPrezzi(Long stockId) throws SQLException {
        List<Double> prezzi = new ArrayList<>();
        String sql = "SELECT prezzo_registrato FROM Last_prices WHERE stock_id = ? ORDER BY data_simulazione ASC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, stockId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    prezzi.add(rs.getDouble("prezzo_registrato"));
                }
            }
        }
        return prezzi;
    }
}