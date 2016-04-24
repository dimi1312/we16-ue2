package at.ac.tuwien.big.we16.ue2.controller;

import at.ac.tuwien.big.we16.ue2.beans.Sortiment;
import at.ac.tuwien.big.we16.ue2.beans.User;
import at.ac.tuwien.big.we16.ue2.service.ServiceFactory;
import javafx.scene.control.Alert;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.xml.ws.Service;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The class is called from login.jsp and handels the login of a user.
 * Created by Dimi on 12.04.2016.
 */
public class LoginServlet extends HttpServlet{
    /**
     * The methode handels a Get call on login.jsp by using the post methode.
     * @param req HttpRequest
     * @param resp HttpResponse
     * @throws ServletException if an error occurs
     * @throws IOException if an io error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    /**
     * Handles the HTTP <code>POST</code> method. If the user attemps to log in a new Session is created. From
     * the given parameters (email, password) a new User is going to be created an stored in the session. The user
     * is then redirected to the overview page.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if("login".equals(request.getParameter("login"))) {
            //creates new session and stores new user in it
              HttpSession session = request.getSession();
              String name = request.getParameter("email");
              String passwort =  request.getParameter("password");
              if (!"".equals(name) && !"".equals(passwort)) {
                 User user = new User(request.getParameter("email"), request.getParameter("password"));
                 user.setLoggedIn(true);
                 session.setAttribute("user", user);
                 response.sendRedirect("/views/overview.jsp");
              }
        } else if("logout".equals(request.getParameter("login"))) {
            ((User)request.getSession().getAttribute("user")).setLoggedIn(false);
            request.getSession().invalidate();
            response.sendRedirect("/views/login.jsp");
        }
    }
}
