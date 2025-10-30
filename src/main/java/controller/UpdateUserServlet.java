package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Utente;
import model.UtenteDAO;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet("/update-user-servlet")
public class UpdateUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/userResults/utenteAggiornatoOk.jsp";

        String username = request.getParameter("username");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String data_nascitaStr = request.getParameter("data_nascita");
        String email = request.getParameter("email");
        boolean admin = (request.getParameter("admin")) != null;

        java.util.Date data_nascita;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            data_nascita = format.parse(data_nascitaStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Parse data OK");

        UtenteDAO utenteDAO = new UtenteDAO();
        Utente userToUpdate = utenteDAO.doRetrieveByUsername(username);

        userToUpdate.setNome(nome);
        userToUpdate.setCognome(cognome);
        userToUpdate.setEmail(email);
        userToUpdate.setData_nascita(data_nascita);
        userToUpdate.setAdmin(admin);

        utenteDAO.doUpdateUtente(userToUpdate);

        request.setAttribute("userToUpdate", userToUpdate);
        request.getRequestDispatcher(address).forward(request, response);
    }
}
