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
    
    public void close() {
        try {
            if (conn != null)
                conn.close();
        }
        catch (SQLException sqle) {}
    }

}
