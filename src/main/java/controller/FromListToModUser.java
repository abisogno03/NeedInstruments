package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Utente;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/from-list-to-mod-user")
public class FromListToModUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/admin/aggiornaUtente.jsp";
        HttpSession session = request.getSession();
        Utente u = (Utente) session.getAttribute("utente");

        if (u == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String username = request.getParameter("username");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String data_nascitaStr = request.getParameter("data_nascita");
        String email = request.getParameter("email");
        boolean admin = Boolean.parseBoolean(request.getParameter("admin"));

        Date data_nascita;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            data_nascita = format.parse(data_nascitaStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Parse data OK");

        Utente utente = new Utente(username, nome, cognome, email, data_nascita, admin);

        request.setAttribute("utente", utente);
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }
}
