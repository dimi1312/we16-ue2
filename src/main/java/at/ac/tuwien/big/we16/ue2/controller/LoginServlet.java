package at.ac.tuwien.big.we16.ue2.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Dimi on 12.04.2016.
 */
public class LoginServlet extends HttpServlet{

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {



            HttpSession session = request.getSession(true);
            String name = (String) request.getParameter("email");
            String passwort = (String) request.getParameter("password");
            session.setAttribute("user", name);
            if (!"".equals(name) && "".equals(passwort)) {
                request.getServletContext().getRequestDispatcher("/views/overview.jsp").forward(request, response);
            }


    }
}
