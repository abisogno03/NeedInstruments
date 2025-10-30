package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;
import model.ProdottoDAO;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/search-servlet")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/product/search.jsp";
        String search = request.getParameter("search");
        String sortBy = request.getParameter("sortBy");
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        List<Prodotto> prodotti = prodottoDAO.doRetrieveBySearch(search);

        if (sortBy != null) {
            switch (sortBy) {
                case "prezzo-asc":
                    prodotti = prodotti.stream()
                            .sorted(Comparator.comparingDouble(Prodotto::getPrezzo))
                            .collect(Collectors.toList());
                    break;
                case "prezzo-desc":
                    prodotti = prodotti.stream()
                            .sorted(Comparator.comparingDouble(Prodotto::getPrezzo).reversed())
                            .collect(Collectors.toList());
                    break;
                case "alfabetico":
                    prodotti = prodotti.stream()
                            .sorted(Comparator.comparing(Prodotto::getMarca).thenComparing(Prodotto::getModello))
                            .collect(Collectors.toList());
                    break;
            }
        }

        request.setAttribute("prodotti", prodotti);
        request.setAttribute("search", search);

        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            // Risponde con il frammento jsp per AJAX
            request.getRequestDispatcher("/WEB-INF/product/productList.jsp").forward(request, response);
        } else {
            // Risponde con l'intera pagina
            request.getRequestDispatcher(address).forward(request, response);
        }
    }
}
