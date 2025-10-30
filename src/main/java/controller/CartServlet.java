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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cart-servlet")
public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/user/carrello.jsp";
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        Carrello carrello;
        CarrelloDAO carrelloDAO = new CarrelloDAO();
        List<Prodotto> listaProdotti = new ArrayList<>();

        if (utente != null) {
            // Utente loggato
            carrello = carrelloDAO.getCarrelloByUtente(utente.getUsername());
        } else {
            // Utente non loggato
            carrello = (Carrello) session.getAttribute("carrello");
        }
        if (carrello != null) {
            listaProdotti = carrello.getProdotti();
        }


        request.setAttribute("listaProdotti", listaProdotti);
        request.getRequestDispatcher(address).forward(request, response);
    }
}

