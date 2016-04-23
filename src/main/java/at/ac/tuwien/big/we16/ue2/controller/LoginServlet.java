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
 * Created by Dimi on 12.04.2016.
 */
public class LoginServlet extends HttpServlet{
    //public static Sortiment sortiment = new Sortiment();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if("login".equals(request.getParameter("login"))) {
              HttpSession session = request.getSession();
              String name = request.getParameter("email");
              String passwort =  request.getParameter("password");
              if (!"".equals(name) && !"".equals(passwort)) {
                 User user = new User(request.getParameter("email"), request.getParameter("password"));
                 session.setAttribute("user", user);
                  Sortiment sortiment = (Sortiment)session.getAttribute("sortiment");
                  if(sortiment == null) {
                      sortiment = new Sortiment();
                      ServiceFactory.getNotifierService().setSortiment(sortiment);
                      ServiceFactory.getNotifierService().startComputerUser();
                      this.getServletContext().setAttribute("sortiment", sortiment);
                  }
                  response.sendRedirect("/views/overview.jsp");
                //  request.getServletContext().getRequestDispatcher("/views/overview.jsp").forward(request, response);
                  return;
              }
        }
    }
}
