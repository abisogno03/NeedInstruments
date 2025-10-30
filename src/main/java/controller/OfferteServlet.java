package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Prodotto;
import model.ProdottoDAO;
import model.Utente;

import java.io.IOException;
import java.util.List;

@WebServlet("/offerte-servlet")
public class OfferteServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/product/offerte.jsp";

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        List<Prodotto> productList = prodottoDAO.getOfferte();

        request.setAttribute("productList", productList);

        request.getRequestDispatcher(address).forward(request, response);
    }
}
