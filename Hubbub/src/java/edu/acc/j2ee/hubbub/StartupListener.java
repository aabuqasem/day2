package edu.acc.j2ee.hubbub;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /*
        UserDao users = new UserDao();
        User johndoe = new User("johndoe", "P@ssw0rd", null);
        johndoe = users.register(johndoe);
        sce.getServletContext().setAttribute("users", users);
        PostDao posts = new PostDao();
        Post post = new Post(johndoe,"Hey! It's my first Hubbub Post! Whee!!",LocalDateTime.now());
        posts.post(post);
        sce.getServletContext().setAttribute("posts", posts);
        */
        String url = sce.getServletContext().getInitParameter("db.url");
        String user = sce.getServletContext().getInitParameter("db.user");
        String pass = sce.getServletContext().getInitParameter(("db.pass"));
        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            DbUserDao users = new DbUserDao(conn);
            DbPostDao posts = new DbPostDao(conn, users);
            sce.getServletContext().setAttribute("users", users);
            sce.getServletContext().setAttribute("posts", posts);
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DbUserDao dao = (DbUserDao)sce.getServletContext().getAttribute("DbUserDao");
        dao.close();
    }
}
