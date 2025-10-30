package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Utente;
import model.UtenteDAO;

import java.io.IOException;

@WebServlet("/from-profile-to-mod-password")
public class FromProfileToModPassword extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/user/modPassword.jsp";

        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        if (utente != null) {
            UtenteDAO utenteDAO = new UtenteDAO();
            String passwordVecchia = utenteDAO.getPasswordByUsername(utente.getUsername());

            request.setAttribute("passwordVecchia", passwordVecchia);
            request.getRequestDispatcher(address).forward(request, response);
        } else {
            response.sendRedirect("/login.jsp");
        }
    }
}
