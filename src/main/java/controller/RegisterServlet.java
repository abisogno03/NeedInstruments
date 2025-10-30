package controller;

import jakarta.servlet.RequestDispatcher;
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
import java.util.Date;

@WebServlet("/register-servlet")
public class RegisterServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String address = "/login.jsp";

        String username = request.getParameter("username");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String data_nascitaStr = request.getParameter("data_nascita");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Date data_nascita;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            data_nascita = format.parse(data_nascitaStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Parse data OK");

        if(password.equals(request.getParameter("conferma_password"))){
            System.out.println("Le password coincidono. 1: " + request.getParameter("password")
                    + " 2: " + request.getParameter("conferma_password"));
            Utente u = new Utente();
            u.setUsername(username);
            u.setPassword(password);
            u.setNome(nome);
            u.setEmail(email);
            u.setCognome(cognome);
            u.setData_nascita(data_nascita);

            UtenteDAO ud = new UtenteDAO();
            ud.doSave(u);
            request.setAttribute("utente", u);
            System.out.println("Utente " + u.getUsername() + " salvato");

            RequestDispatcher dispatcher = request.getRequestDispatcher(address);
            dispatcher.forward(request,response);
        }
        else{
            System.out.println("Non è stato salvato nessun utente");
        }
    }
}
