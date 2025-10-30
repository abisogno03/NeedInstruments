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

@WebServlet("/update-fav-servlet")
public class UpdateFavServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/prodResults/preferitoAggiuntoOK.jsp";
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        int id = Integer.parseInt(request.getParameter("id"));

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        Prodotto prodotto = prodottoDAO.doRetrieveById(id);

        Preferiti preferiti;
        if (utente != null) { // Utente loggato
            PreferitiDAO prefdao = new PreferitiDAO();
            preferiti = prefdao.getFavoritesByUtente(utente.getUsername());
            if (preferiti == null) {
                preferiti = new Preferiti(new ArrayList<>(), utente);
            }

            // Controlla se il prodotto è già nei preferiti
            if (preferiti.getLista().contains(prodotto)) {
                request.getRequestDispatcher("/WEB-INF/prodResults/copiaProdottoPreferito.jsp").forward(request, response);
                return;
            }

            preferiti.getLista().add(prodotto);
            prefdao.updatePreferiti(preferiti.getUtente().getUsername(), id);
        } else { // Utente non loggato
            preferiti = (Preferiti) session.getAttribute("preferiti");
            if (preferiti == null) {
                preferiti = new Preferiti(new ArrayList<>(), null);
            }

            // Controlla se il prodotto è già nei preferiti
            if (preferiti.getLista().contains(prodotto)) {
                request.getRequestDispatcher("/WEB-INF/prodResults/copiaProdottoPreferito.jsp").forward(request, response);
                return;
            }

            preferiti.getLista().add(prodotto);
            session.setAttribute("preferiti", preferiti);
        }

        request.getRequestDispatcher(address).forward(request, response);
    }
}
