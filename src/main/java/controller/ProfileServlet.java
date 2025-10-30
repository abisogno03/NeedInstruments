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

@WebServlet("/profile-servlet")
public class ProfileServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String address = "/WEB-INF/user/profile.jsp";

        // controlla se l'utente è presente nella sessione
        Utente utente = (Utente) session.getAttribute("utente");

        // se no, recupera l'utente dal db
        if (utente == null) {
            String username = (String) session.getAttribute("username");
            String password = (String) session.getAttribute("password");

            if (username != null && password != null) {
                UtenteDAO ud = new UtenteDAO();
                utente = ud.doRetrieveByUsernamePassword(username, password);

                if (utente != null) {
                    session.setAttribute("utente", utente);
                } else {
                    // l'utente non è presente nel db
                    address = "/login.jsp";
                }
            } else {
                // username o password non presente nella sessione
                address = "/login.jsp";
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }
}
