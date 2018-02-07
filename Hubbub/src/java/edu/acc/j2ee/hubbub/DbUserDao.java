package edu.acc.j2ee.hubbub;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbUserDao {
    private final Connection conn;
    
    public DbUserDao(Connection conn) {
        this.conn = conn;
    }
    
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, username);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setJoined(rs.getDate("joined").toLocalDate());
                    return user;
                }
                return null;
            }
        }
        catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }
    
    public User register(User user) {
        User taken = getUserByUsername(user.getUsername());
        if (taken != null) return null;
        user.setJoined(LocalDate.now());
        String sql = "INSERT INTO users VALUES (?,?,?)";
        try (PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, user.getUsername());
            stat.setString(2, user.getPassword());
            stat.setDate(3, Date.valueOf(user.getJoined()));
            stat.executeUpdate();
            return user;
        }
        catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }
    
    public User authenticate(User user) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, user.getUsername());
            stat.setString(2, user.getPassword());
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    user.setJoined(rs.getDate("joined").toLocalDate());
                    return user;
                }
                return null;
            }
        }
        catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }
    
    public void close() {
        try {
            if (conn != null)
                conn.close();
        }
        catch (SQLException sqle) {}
    }
}
