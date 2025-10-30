package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Utente;
import model.UtenteDAO;

import java.io.IOException;

@WebServlet("/mod-pass-servlet")
public class ModPasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        if (utente != null) {
            String passwordNuova = request.getParameter("passwordNuova");
            String confermaPasswordNuova = request.getParameter("confermaPasswordNuova");

            if (passwordNuova.equals(confermaPasswordNuova)) {
                UtenteDAO utenteDAO = new UtenteDAO();
                utenteDAO.updatePassword(utente.getUsername(), passwordNuova);
                response.sendRedirect("passwordCambiata.jsp");
            } else {
                request.setAttribute("errorMessage", "Le nuove password non coincidono.");
                request.getRequestDispatcher("/WEB-INF/changePassword.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
