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

@WebServlet("/list-prods-servlet")
public class ListProdsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/admin/listaProdotti.jsp";
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        List<Prodotto> productList = prodottoDAO.doRetrieveAll();

        request.setAttribute("productList", productList);

        request.getRequestDispatcher(address).forward(request, response);
    }
}