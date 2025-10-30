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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/add-user-servlet")
public class AddUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/userResults/utenteAggiuntoOk.jsp";
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String username = request.getParameter("username");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String data_nascitaStr = request.getParameter("data_nascita");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        boolean admin = request.getParameter("admin") != null;

        Date data_nascita;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            data_nascita = format.parse(data_nascitaStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Parse data OK");

        Utente newUser = new Utente(username, nome, cognome, email, password, data_nascita, admin);

        UtenteDAO utenteDAO = new UtenteDAO();
        utenteDAO.doSave(newUser);

        request.setAttribute("utente", newUser);
        request.getRequestDispatcher(address).forward(request, response);
    }
}
