package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/preferiti-servlet")
public class PreferitiServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/user/preferiti.jsp";
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Preferiti preferiti;
        PreferitiDAO pfdao = new PreferitiDAO();
        List<Prodotto> listaProdotti = new ArrayList<>();

        if (utente != null) {
            // Utente loggato
            preferiti = pfdao.getFavoritesByUtente(utente.getUsername());
        } else {
            // Utente non loggato
            preferiti = (Preferiti) session.getAttribute("preferiti");
        }
        if (preferiti != null) {
            listaProdotti = preferiti.getLista();
        }

        // Imposta i prodotti del carrello come attributo della richiesta
        request.setAttribute("listaProdotti", listaProdotti);

        // Forward alla pagina JSP
        request.getRequestDispatcher(address).forward(request, response);
    }
}
