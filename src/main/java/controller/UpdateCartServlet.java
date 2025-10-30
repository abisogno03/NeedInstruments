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

@WebServlet("/update-cart-servlet")
public class UpdateCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/prodResults/carrelloAggiuntoOK.jsp";
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        int prodottoId = Integer.parseInt(request.getParameter("id"));

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        Prodotto prodotto = prodottoDAO.doRetrieveById(prodottoId);

        Carrello carrello;
        if (utente != null) {// Utente loggato
            CarrelloDAO carrelloDAO = new CarrelloDAO();
            carrello = carrelloDAO.getCarrelloByUtente(utente.getUsername());
            if (carrello == null) {
                carrello = new Carrello(new ArrayList<>(), utente);
            }

            boolean prodottoPresente = carrello.getProdotti().stream()
                    .anyMatch(p -> p.getId() == prodottoId);
            if (prodottoPresente) {
                // Prodotto già presente nel carrello
                request.getRequestDispatcher("/WEB-INF/prodResults/copiaProdottoCarrello.jsp").forward(request, response);
                return;
            }
            carrello.getProdotti().add(prodotto);
            carrelloDAO.updateCarrello(carrello.getUtente().getUsername(), prodottoId);
        } else { // Utente non loggato
            // A questo punto, controlliamo che non ci sia un oggetto di tipo Preferiti con lo stesso nome
            Object sessionCarrello = session.getAttribute("carrello");
            if (sessionCarrello instanceof Carrello) {
                carrello = (Carrello) sessionCarrello;
            } else {
                carrello = new Carrello(new ArrayList<>(), null);
            }

            carrello.getProdotti().add(prodotto);
            session.setAttribute("carrello", carrello);
        }

        request.getRequestDispatcher(address).forward(request, response);

    }
}
