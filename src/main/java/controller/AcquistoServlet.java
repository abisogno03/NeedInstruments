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

@WebServlet("/acquisto-servlet")
public class AcquistoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/purchase/acquistoOK.jsp";

        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Carrello carrello;
        CarrelloDAO cdao = new CarrelloDAO();
        List<Prodotto> listaProdotti = new ArrayList<>();

        if (utente != null) {
            // Utente loggato
            carrello = cdao.getCarrelloByUtente(utente.getUsername());
        } else {
            // Utente non loggato
            carrello = (Carrello) session.getAttribute("listaProdotti");
        }
        if (carrello != null) {
            listaProdotti = carrello.getProdotti();
        }

        String carta = request.getParameter("carta");
        String cvc = request.getParameter("cvc");
        String via = request.getParameter("via");

        if (carta == null || cvc == null || via == null ||
                !carta.matches("\\d{4} \\d{4} \\d{4} \\d{4}") || !cvc.matches("\\d{3}")) {
            request.setAttribute("errorMessage", "Inserisci tutti i campi correttamente.");
            request.getRequestDispatcher("/WEB-INF/purchase/pagamento.jsp").forward(request, response);
            return;
        }


        for(Prodotto prodotto : listaProdotti) {
            int numero = Integer.parseInt(request.getParameter("quantita-" + prodotto.getId()));
            Acquisto acquisto = new Acquisto(prodotto, utente, numero);
            AcquistoDAO acqdao = new AcquistoDAO();

            acqdao.doPurchase(acquisto);
            cdao.removeFromCarrello(acquisto.getUtente().getUsername(), acquisto.getProdotto().getId());
        }

        request.getRequestDispatcher(address).forward(request, response);
    }
}
