package at.ac.tuwien.big.we16.ue2.controller;

import at.ac.tuwien.big.we16.ue2.beans.Auction;
import at.ac.tuwien.big.we16.ue2.beans.Sortiment;
import at.ac.tuwien.big.we16.ue2.beans.User;
import at.ac.tuwien.big.we16.ue2.service.ServiceFactory;
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
            HttpSession session = request.getSession();
            double gebotener_preis = Double.parseDouble(request.getParameter("price"));
            gebotener_preis = Math.round(gebotener_preis * 100)/100.0;
            Auction a = (Auction) session.getAttribute("product");
            User u = (User) session.getAttribute("user");
            synchronized(a) {
                synchronized(u) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    String status = "success";
                    if((u.getMoney() - gebotener_preis) < 0 || gebotener_preis <= a.getHoechstgebot()) {
                        status = "error";
                    } else {
                        User loser = a.getHoechstbietender();
                        synchronized(loser) {
                            u.changeMoney(gebotener_preis * (-1));
                            if (!a.containsUser(u)) {
                                u.addAuction();
                                a.addUser(u);
                            }
                            loser.changeMoney(a.getHoechstgebot());
                            ServiceFactory.getNotifierService().ueberboten(a,loser, loser.getMoney());

                            a.setHoechstbietender(u);
                            a.setHoechstgebot(gebotener_preis);
                            ServiceFactory.getNotifierService().gebotAbgegeben(a, u, gebotener_preis);
                        }
                    }
                    response.getWriter().write("{\"price\": \"" + u.getMoney() + "\", \"anzahl\": \"" + u.getAuctions() + "\", \"status\": \"" + status + "\"}");
                }
            }
    }
}
