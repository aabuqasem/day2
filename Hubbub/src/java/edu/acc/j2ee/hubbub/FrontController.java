
package edu.acc.j2ee.hubbub;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrontController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = "timeline", destination = "timeline";
        String requestAction = request.getParameter("action");
        if (requestAction != null) action = requestAction;
        switch (action) {
            case "timeline": destination = timeline(request); break;
            case "login": destination = login(request); break;
            case "join": destination = join(request); break;
            case "logout": destination = logout(request); break;
            case "post": destination = post(request); break;
            default:
        }
        
        request.getRequestDispatcher("/WEB-INF/views/" + destination + ".jsp")
                .forward(request, response);
    }
    
    private String login(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") != null)
            return timeline(request);
        if (request.getMethod().equalsIgnoreCase("GET"))
            return "login";
        else {
            DbUserDao users = getUserDao();
            User user = new User(
                    request.getParameter("username"),
                    request.getParameter("password"),
                    null
            );
            user.validate(false);
            if (user.hasErrors()) {
                request.setAttribute("errors", user.getErrors());
                return "login";
            }  
            user = users.authenticate(user);
            if (user == null) {
                request.setAttribute("flash", "Access Denied");
                return "login";
            } else {
                request.getSession().setAttribute("user", user);
                return timeline(request);
            }
        }
    }
    
    private String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return timeline(request);
    }
    
    private String join(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") != null)
            return timeline(request);
        if (request.getMethod().equalsIgnoreCase("GET"))
            return "join";
        else {
            if (!request.getParameter("password1").equals(request.getParameter("password2"))) {
                request.setAttribute("flash", "Password fields don't match");
                return "join";
            }              
            DbUserDao users = getUserDao();
            User user = new User(
                    request.getParameter("username"),
                    request.getParameter("password1"),
                    null
            );
            user.validate(false);
            if (user.hasErrors()) {
                request.setAttribute("errors", user.getErrors());
                return "join";
            }
            user = users.register(user);
            if (user == null) {
                request.setAttribute("flash", "That username is unavailable");
                return "join";
            } else {
                request.getSession().setAttribute("user", user);
                return timeline(request);                
            }
        }
    }
    
    private String timeline(HttpServletRequest request) {
        DbPostDao dao = getPostDao();
        List<Post> posts = dao.getAllPosts(0, 10);
        request.setAttribute("posts", posts);
        return "timeline";
    }
    
    private String post(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null)
            return timeline(request);
        if (request.getMethod().equalsIgnoreCase("GET"))
            return "post";
        else {
            DbPostDao posts = getPostDao();
            User author = (User)request.getSession().getAttribute("user");
            Post post = new Post(author, request.getParameter("content"), LocalDateTime.now(), 0);
            post.validate(false);
            if (post.hasErrors()) {
                request.setAttribute("errors", post.getErrors());
                return "post";
            }
            posts.post(post);
            return timeline(request);
        }
    }
    
    private DbUserDao getUserDao() {
        return (DbUserDao)getServletContext().getAttribute("users");
    }
    
    private DbPostDao getPostDao() {
        return (DbPostDao)getServletContext().getAttribute("posts");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
