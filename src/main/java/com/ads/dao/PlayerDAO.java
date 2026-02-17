package com.ads.dao;

import com.ads.entity.Player;
import java.sql.*;

public class PlayerDAO {
    private Connection connection;

    public PlayerDAO(Connection connection) {
        this.connection = connection;
    }

    public Player findById(Long id) throws SQLException {
        String sql = "SELECT * FROM Player WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Player p = new Player();
                    p.setId(rs.getLong("id"));
                    p.setUsername(rs.getString("username"));
                    p.setBudgetCorrente(rs.getDouble("budget_corrente"));
                    p.setDataCreazione(rs.getTimestamp("data_creazione"));
                    return p;
                }
            }
        }
        return null;
    }

    public void updateBudget(Long id, double nuovoBudget) throws SQLException {
        String sql = "UPDATE Player SET budget_corrente = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, nuovoBudget);
            ps.setLong(2, id);
            ps.executeUpdate();
        }
    }
}