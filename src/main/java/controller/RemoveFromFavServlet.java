package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/remove-from-fav-servlet")
public class RemoveFromFavServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/prodResults/preferitoRimossoOK.jsp";
        HttpSession session = request.getSession();
        Preferiti preferiti = (Preferiti) session.getAttribute("preferiti");
        Utente utente = (Utente) session.getAttribute("utente");

        if (preferiti == null) {
            preferiti = new Preferiti(); // Crea nuovi preferiti se non esiste ancora nella sessione
        }

        int id = Integer.parseInt(request.getParameter("id"));

        if (utente != null) {
            PreferitiDAO prfdao = new PreferitiDAO();
            prfdao.removeFromPreferiti(utente.getUsername(), id);
        } else {
            List<Prodotto> listaProdotti = preferiti.getLista();
            listaProdotti.removeIf(prodotto -> prodotto.getId() == id);
            preferiti.setLista(listaProdotti);
        }

        session.setAttribute("preferiti", preferiti);

        // Rimanda alla conferma di rimozione
        request.getRequestDispatcher(address).forward(request, response);
    }
}
