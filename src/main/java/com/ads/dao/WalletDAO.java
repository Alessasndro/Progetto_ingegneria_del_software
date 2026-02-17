package com.ads.dao;
import com.ads.entity.Wallet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WalletDAO {
    private Connection conn;
    public WalletDAO(Connection conn) { this.conn = conn; }

    public void save(Wallet w) throws SQLException {
        String sql = "INSERT INTO Wallet (player_id, stock_id, quantita, prezzo_carico) VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE quantita = quantita + ?, prezzo_carico = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, w.getPlayerId()); ps.setLong(2, w.getStockId());
            ps.setInt(3, w.getQuantita()); ps.setDouble(4, w.getPrezzoCarico());
            ps.setInt(5, w.getQuantita()); ps.setDouble(6, w.getPrezzoCarico());
            ps.executeUpdate();
        }
    }

    public Wallet findByPlayerAndStock(Long pId, Long sId) throws SQLException {
        String sql = "SELECT * FROM Wallet WHERE player_id = ? AND stock_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, pId); ps.setLong(2, sId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Wallet w = new Wallet();
                w.setQuantita(rs.getInt("quantita"));
                w.setPrezzoCarico(rs.getDouble("prezzo_carico"));
                return w;
            }
        }
        return null;
    }

    public List<Wallet> findByPlayerId(Long pId) throws SQLException {
        List<Wallet> list = new ArrayList<>();
        String sql = "SELECT * FROM Wallet WHERE player_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, pId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Wallet w = new Wallet();
                w.setStockId(rs.getLong("stock_id"));
                w.setQuantita(rs.getInt("quantita"));
                w.setPrezzoCarico(rs.getDouble("prezzo_carico"));
                list.add(w);
            }
        }
        return list;
    }

    public void updateQuantita(Long pId, Long sId, int qty) throws SQLException {
        String sql = "UPDATE Wallet SET quantita = ? WHERE player_id = ? AND stock_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, qty); ps.setLong(2, pId); ps.setLong(3, sId);
            ps.executeUpdate();
        }
    }

    public void delete(Long pId, Long sId) throws SQLException {
        String sql = "DELETE FROM Wallet WHERE player_id = ? AND stock_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, pId); ps.setLong(2, sId);
            ps.executeUpdate();
        }
    }
}