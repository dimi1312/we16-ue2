package at.ac.tuwien.big.we16.ue2.controller;

import at.ac.tuwien.big.we16.ue2.beans.Sortiment;
import at.ac.tuwien.big.we16.ue2.beans.User;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by Dimi on 19.04.2016.
 */
public class BietenServlet extends HttpServlet{
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
        if("Bieten".equals(request.getParameter("submit-price"))) {
            HttpSession session = request.getSession(true);

            double gebotener_preis = Double.parseDouble(request.getParameter("new-price"));

/*

            if (!"".equals(name) && !"".equals(passwort)) {
                User user = new User(request.getParameter("email"), request.getParameter("password"));
                session.setAttribute("user", user);
                Sortiment sortiment = (Sortiment)session.getAttribute("sortiment");
                if(sortiment == null) {
                    sortiment = new Sortiment();
                    // session.setAttribute("sortiment", sortiment);
                    this.getServletContext().setAttribute("sortiment", sortiment);
                }
                Cookie cookie = new Cookie("sessionId", session.getId());
                response.addCookie(cookie);
                request.getServletContext().getRequestDispatcher("/views/overview.jsp").forward(request, response);
            }*/
        }
    }
}