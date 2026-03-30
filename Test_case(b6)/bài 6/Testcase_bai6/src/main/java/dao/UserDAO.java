package dao;

import model.User;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // ─── INSERT ──────────────────────────────────────────────────────────────
    public boolean insert(User u) throws Exception {
        String sql = "INSERT INTO users (username, fullname, password, email) VALUES (?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getFullname());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getEmail());
            return ps.executeUpdate() > 0;
        }
    }

    // ─── UPDATE ──────────────────────────────────────────────────────────────
    public boolean update(User u) throws Exception {
        String sql = "UPDATE users SET fullname=?, password=?, email=? WHERE username=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getFullname());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getUsername());
            return ps.executeUpdate() > 0;
        }
    }

    // ─── DELETE ──────────────────────────────────────────────────────────────
    public boolean delete(String username) throws Exception {
        String sql = "DELETE FROM users WHERE username=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            return ps.executeUpdate() > 0;
        }
    }

    // ─── GET BY USERNAME ─────────────────────────────────────────────────────
    public User getByUsername(String username) throws Exception {
        String sql = "SELECT * FROM users WHERE username=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getString("username"),
                    rs.getString("fullname"),
                    rs.getString("password"),
                    rs.getString("email")
                );
            }
        }
        return null;
    }

    // ─── GET ALL ─────────────────────────────────────────────────────────────
    public List<User> getAll() throws Exception {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY username";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new User(
                    rs.getString("username"),
                    rs.getString("fullname"),
                    rs.getString("password"),
                    rs.getString("email")
                ));
            }
        }
        return list;
    }

    // ─── CHECK EXISTS ─────────────────────────────────────────────────────────
    public boolean exists(String username) throws Exception {
        return getByUsername(username) != null;
    }
}