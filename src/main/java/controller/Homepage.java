package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;
import model.ProdottoDAO;

import java.io.IOException;
import java.util.List;

@WebServlet("/homepage")
public class Homepage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/Homepage.jsp";
        ProdottoDAO pdao1 = new ProdottoDAO();
        ProdottoDAO pdao2 = new ProdottoDAO();

        List<Prodotto> topSoldProducts = pdao1.getTopSoldProducts();
        List<Prodotto> lastSoldProducts = pdao2.getLastSoldProduct();

        request.setAttribute("topSoldProducts", topSoldProducts);
        request.setAttribute("lastSoldProducts", lastSoldProducts);

        request.getRequestDispatcher(address).forward(request, response);
    }
}
