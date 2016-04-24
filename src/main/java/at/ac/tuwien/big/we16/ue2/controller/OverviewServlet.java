package at.ac.tuwien.big.we16.ue2.controller;

import at.ac.tuwien.big.we16.ue2.beans.Auction;
import at.ac.tuwien.big.we16.ue2.beans.Sortiment;
import at.ac.tuwien.big.we16.ue2.service.ServiceFactory;

import javax.servlet.RequestDispatcher;
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
     * Postcondition:
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

}
