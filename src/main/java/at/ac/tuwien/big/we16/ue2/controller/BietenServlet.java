package at.ac.tuwien.big.we16.ue2.controller;

import at.ac.tuwien.big.we16.ue2.beans.Auction;
import at.ac.tuwien.big.we16.ue2.beans.Sortiment;
import at.ac.tuwien.big.we16.ue2.beans.User;
import javafx.scene.control.Alert;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by Dimi on 19.04.2016.
 */
@WebServlet(name="/controller/BietenServlet", urlPatterns={"/controller/BietenServlet"})
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
        String n = request.getParameter("price");
        response.setContentType("application/json");
        response.getWriter().write("{\"price\":\"" + n + "\"}");
       /* HttpSession session = request.getSession();
        double gebotener_preis = Double.parseDouble(request.getParameter("price"));
        Auction a = (Auction) session.getAttribute("product");
        User u = (User) session.getAttribute("user");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"price\": \"1400\"}");*//*
        if("Bieten".equals(request.getParameter("submit-price"))) {
            HttpSession session = request.getSession();
            double gebotener_preis = Double.parseDouble(request.getParameter("new-price"));
            Auction a = (Auction) session.getAttribute("product");
            User u = (User) session.getAttribute("user");
         //   response.setContentType("application/json");
         //   response.setCharacterEncoding("utf-8");
            if (a.getHoechstgebot() >= gebotener_preis) {
             //   response.getWriter().write("{\"auktionen\": \"" + u.getAuctions() + "\", \"kontostand\": \"" + u.getMoney() + "\", \"display\":\"none\"}");
            } else {// response.getWriter().write("{\"auktionen\": \"" + u.getAuctions() + "\", \"kontostand\": \"" + u.getMoney() + "\", \"display\":\"none\"}");
            }
           response.setContentType("text/plain");
           response.setCharacterEncoding("UTF-8");
           response.getWriter().write(String.valueOf(1400));
        }*/
    }
}
