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

@WebServlet("/list-acquisto-servlet")
public class ListAcquistoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/admin/listaAcquisti.jsp";
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        AcquistoDAO acqdao = new AcquistoDAO();
        List<Acquisto> productList = acqdao.doRetrieveAll();

        ProdottoDAO pdao = new ProdottoDAO();
        List<Prodotto> prodottos = new ArrayList<>();
        for (Acquisto acquisto : productList) {
            Prodotto p = pdao.doRetrieveById(acquisto.getProdotto().getId());
            prodottos.add(p);
        }

        request.setAttribute("prodottos", prodottos);
        request.setAttribute("productList", productList);
        request.getRequestDispatcher(address).forward(request, response);
    }
}
