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

@WebServlet("/login-servlet")
public class LoginServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String address;
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UtenteDAO ud = new UtenteDAO();
        Utente u = ud.doRetrieveByUsernamePassword(username, password);

        HttpSession session = request.getSession();
        if(u != null){
            session.setAttribute("utente", u);

            request.setAttribute("utente",u);

            System.out.println("Utente " + u.getUsername() + " recuperato dalla sessione");
            address = "/homepage";
        }
        else{
            session.setAttribute("loginError", "Nome utente e/o password errati. Riprova.");
            address = "/login.jsp";
        }

        response.sendRedirect(request.getContextPath() + address);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
