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
import java.util.List;

@WebServlet("/list-users-servlet")
public class ListUsersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/admin/listaUtenti.jsp";
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        UtenteDAO utenteDAO = new UtenteDAO();
        List<Utente> usersList = utenteDAO.doRetrieveAll();

        request.setAttribute("usersList", usersList);

        request.getRequestDispatcher(address).forward(request, response);
    }
}
