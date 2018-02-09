package edu.acc.j2ee.hubbub;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class DbPostDao {
    private final Connection conn;
    private final DbUserDao users;
    
    public DbPostDao(Connection conn, DbUserDao users) {
        this.conn = conn;
        this.users = users;
    }
    
    public Post post(Post post) {
        String sql = "INSERT INTO POSTS (author,content,posted) VALUES (?,?,?)";
        try (PreparedStatement stat = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stat.setString(1, post.getAuthor().getUsername());
            stat.setString(2, post.getContent());
            stat.setTimestamp(3, Timestamp.valueOf(post.getPosted()));
            stat.executeUpdate();
            try (ResultSet rs = stat.getGeneratedKeys()) {
                rs.next();
                post.setId(rs.getInt(1));
                return post;
            }
        }
        catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    public List<Post> getAllPosts(int offset, int length) {
        String sql = "SELECT * FROM posts ORDER BY posted DESC OFFSET " + offset +
                " ROWS FETCH NEXT " + length + " ROWS ONLY";
        List<Post> posts = new ArrayList<>();
        try (Statement stat = conn.createStatement();
             ResultSet rs = stat.executeQuery(sql)) {
            while (rs.next()) {
                Post post = new Post();
                User author = users.getUserByUsername(rs.getString("author"));
                post.setAuthor(author);
                post.setContent(rs.getString("content"));
                post.setPosted(rs.getTimestamp("posted").toLocalDateTime());
                post.setId(rs.getInt("id"));
                posts.add(post);
            }
            return posts;
        }
        catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }
    
    public void close() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException sqle) {}
    }

    public List<Post> search(String[] terms) {
        List<Post> results = new ArrayList<>();
        if (terms.length < 1) return results;
        String sql = "SELECT * FROM posts WHERE UPPER(content) LIKE UPPER(";
        for (int i = 0; i < terms.length - 1; i++)
            sql += "'%" + terms[i] + "%') AND UPPER(content) LIKE UPPER(";
        sql += "'%" + terms[terms.length - 1] + "%')";
        try (Statement stat = conn.createStatement();
             ResultSet rs = stat.executeQuery(sql)) {
            while (rs.next()) {
                Post result = new Post();
                User author = users.getUserByUsername(rs.getString("author"));
                result.setAuthor(author);
                result.setContent(rs.getString("content"));
                result.setPosted(rs.getTimestamp("posted").toLocalDateTime());
                result.setId(rs.getInt("id"));
                results.add(result);
            }
            return results;
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    List<Post> getPostsByUsername(String whoFor) {
        String sql = "SELECT * FROM posts WHERE author='" + whoFor + "' ORDER BY posted DESC";
        List<Post> posts = new ArrayList<>();
        try (Statement stat = conn.createStatement();
             ResultSet rs = stat.executeQuery(sql)) {
            User author = users.getUserByUsername(whoFor);
            while (rs.next()) {
                Post post = new Post();
                post.setAuthor(author);
                post.setContent(rs.getString("content"));
                post.setPosted(rs.getTimestamp("posted").toLocalDateTime());
                post.setId(rs.getInt("id"));
                posts.add(post);
            }
        }
        catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
        return posts;
    }
}
