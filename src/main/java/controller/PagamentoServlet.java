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
import java.util.*;

@WebServlet("/pagamento-servlet")
public class PagamentoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String address = "/WEB-INF/purchase/pagamento.jsp";
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Carrello carrello = (Carrello) session.getAttribute("carrello");
        CarrelloDAO carrelloDAO = new CarrelloDAO();
        List<Prodotto> listaProdotti = new ArrayList<>();

        if (utente != null) {
            // Utente loggato
            carrello = carrelloDAO.getCarrelloByUtente(utente.getUsername());
        } else {
            // Utente non loggato
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
        if (carrello != null) {
            listaProdotti = carrello.getProdotti();
        }

        // Imposta i prodotti del carrello come attributo della richiesta
        request.setAttribute("listaProdotti", listaProdotti);

        // Forward alla pagina JSP
        request.getRequestDispatcher(address).forward(request, response);
    }
}

