import java.sql.*;

public class JDBCTest {
	public static void main(String[] args) {
		String connUrl = "jdbc:derby://localhost:1527/hubbub";
		
		try (
			Connection conn = DriverManager.getConnection(connUrl, "javauser","javauser");
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("SELECT * FROM posts")
		)
		{
			while (rs.next()) {
				String author = rs.getString("author").trim();
				String content = rs.getString("content");
				Timestamp posted = rs.getTimestamp("posted");
				int    id       = rs.getInt("id");
				System.out.printf("Post[author:%s, content:%s, posted:%s, id:%d]\n",
					author, content, posted, id);
			}
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		/*
		try (
			Connection conn = DriverManager.getConnection(connUrl, "javauser","javauser");
			Statement stat = conn.createStatement();
		)
		{
			String sql = String.format("INSERT INTO posts (author,content) VALUES ('%s','%s')", args[0], args[1]);			
			stat.executeUpdate(sql);
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		*/
	}
}