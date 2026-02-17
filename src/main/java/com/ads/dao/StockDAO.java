package com.ads.dao;

import com.ads.entity.Stock;
import java.sql.*;

public class StockDAO {
    private Connection connection;

    public StockDAO(Connection connection) {
        this.connection = connection;
    }

    public Stock findById(Long id) throws SQLException {
        String sql = "SELECT * FROM Stock WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Stock s = new Stock();
                    s.setId(rs.getLong("id"));
                    s.setTicker(rs.getString("ticker"));
                    s.setNomeSocieta(rs.getString("nome_societa"));
                    s.setPrezzoAttuale(rs.getDouble("prezzo_attuale"));
                    return s;
                }
            }
        }
        return null;
    }
}