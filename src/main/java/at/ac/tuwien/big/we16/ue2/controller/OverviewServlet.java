package at.ac.tuwien.big.we16.ue2.controller;

import at.ac.tuwien.big.we16.ue2.beans.Auction;
import at.ac.tuwien.big.we16.ue2.beans.Sortiment;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * The class is called from overview.jsp and forwards to the selected product
 * Created by vanessa on 15.04.16.
 */
public class OverviewServlet extends HttpServlet {
    /**
     * Postcondition: The methode searches for the selected product and stores it in the session. Then
     * there is a redirect to the details site to view the selected product.
     * @param req HttpRequest
     * @param resp HttpResponse
     * @throws ServletException if an error occurs
     * @throws IOException if an io error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("param");
        Sortiment sortiment = (Sortiment)this.getServletContext().getAttribute("sortiment");
        Auction p = sortiment.getProductById(name);
        HttpSession session = null;
        session = req.getSession();
        session.setAttribute("product",p);
        resp.sendRedirect("/views/details.jsp");
    }

    /**
     * Postcondition: The methode calls the doGet methode.
     * @param req HttpRequest
     * @param resp HttpResponse
     * @throws ServletException if an error occurs
     * @throws IOException if an io error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

}
