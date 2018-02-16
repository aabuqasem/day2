package edu.acc.j2ee.hubbub;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbProfileDao {
    private final Connection conn;
    private final DbUserDao users;

    public DbProfileDao(Connection conn, DbUserDao users) {
        this.conn = conn;
        this.users = users;
    }
    
    public Profile add(Profile profile) {
        String sql =
        "INSERT INTO profiles (owner,first_name,last_name,email,biography) VALUES (?,?,?,?,?)";
        try (PreparedStatement stat = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stat.setString(1, profile.getOwner().getUsername());
            stat.setString(2, profile.getFirstName());
            stat.setString(3, profile.getLastName());
            stat.setString(4, profile.getEmail());
            stat.setString(5, profile.getBiography());
            stat.executeUpdate();
            try (ResultSet rs = stat.getGeneratedKeys()) {
                rs.next();
                profile.setId(rs.getInt(1));
                return profile;
            }
        }
        catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }
    
    public Profile getProfileFor(String target) {
        final String sql = "SELECT * FROM profiles WHERE owner = ?";
        try (PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, target);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    User owner = users.getUserByUsername(target);
                    Profile profile = new Profile();
                    profile.setOwner(owner);
                    profile.setBiography(rs.getString("biography"));
                    profile.setEmail(rs.getString("email"));
                    profile.setFirstName(rs.getString("first_name"));
                    profile.setLastName(rs.getString("last_name"));
                    profile.setId(rs.getInt("id"));
                    return profile;
                }
                else return null;
            }
        }
        catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }
    
    public Profile updateProfile(Profile profile) {
        final String sql = "UPDATE profiles SET first_name = ?, last_name = ?, email = ?, biography = ? WHERE owner = ?";
        try (PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, profile.getFirstName());
            stat.setString(2, profile.getLastName());
            stat.setString(3, profile.getEmail());
            stat.setString(4, profile.getBiography());
            stat.setString(5, profile.getOwner().getUsername());
            if (stat.executeUpdate() > 0)
                return profile;
            else return null;
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
