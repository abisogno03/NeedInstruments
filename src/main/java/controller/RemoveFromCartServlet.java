package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Carrello;
import model.CarrelloDAO;
import model.Prodotto;
import model.Utente;

import java.io.IOException;
import java.util.List;

@WebServlet("/remove-from-cart-servlet")
public class RemoveFromCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/prodResults/carrelloRimossoOK.jsp";
        HttpSession session = request.getSession();
        Carrello carrello = (Carrello) session.getAttribute("carrello");
        Utente utente = (Utente) session.getAttribute("utente");

        if (carrello == null) {
            carrello = new Carrello(); // Creare un nuovo carrello se non esiste ancora nella sessione
        }

        int id = Integer.parseInt(request.getParameter("id"));

        if (utente != null) {
            CarrelloDAO carrelloDAO = new CarrelloDAO();
            carrelloDAO.removeFromCarrello(utente.getUsername(), id);
        } else {
            List<Prodotto> listaProdotti = carrello.getProdotti();
            listaProdotti.removeIf(prodotto -> prodotto.getId() == id);
            carrello.setProdotti(listaProdotti);
        }

        session.setAttribute("carrello", carrello);

        // Rimanda alla conferma di rimozione
        request.getRequestDispatcher(address).forward(request, response);
    }
}
