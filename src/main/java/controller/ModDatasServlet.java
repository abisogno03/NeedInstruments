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

@WebServlet("/mod-datas-servlet")
public class ModDatasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String address = "/WEB-INF/userResults/datiModificatiOk.jsp";

        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        String username = request.getParameter("username");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String data_nascitaStr = request.getParameter("data_nascita");

        Date data_nascita;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            data_nascita = format.parse(data_nascitaStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Parse data OK");
        if(utente != null) {
            utente.setNome(nome);
            utente.setCognome(cognome);
            utente.setEmail(email);
            utente.setData_nascita(data_nascita);

            UtenteDAO utenteDAO = new UtenteDAO();
            utenteDAO.doUpdateUtente(utente);

            session.setAttribute("utente", utente);
            response.sendRedirect(request.getContextPath() + "/profile.jsp");
        }
        else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }

        request.getRequestDispatcher(address).forward(request, response);
    }
}

