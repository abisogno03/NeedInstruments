package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ProdottoDAO;

import java.io.IOException;

@WebServlet("/remove-prodotto-servlet")
public class RemoveProdServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/prodResults/prodottoRimossoOk.jsp";
        int id = Integer.parseInt(request.getParameter("id"));

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        prodottoDAO.removeProdottoById(id);

        request.setAttribute("prodotto", prodottoDAO.doRetrieveById(id));
        request.getRequestDispatcher(address).forward(request, response);
    }
}
