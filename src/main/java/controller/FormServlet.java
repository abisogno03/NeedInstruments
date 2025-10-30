package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;
import model.ProdottoDAO;

import java.io.IOException;
import java.util.List;

@WebServlet("/form-servlet")
public class FormServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProdottoDAO pdao = new ProdottoDAO();
        String search = request.getParameter("search");
        List<Prodotto> prodottos = pdao.doRetrieveBySearch(search);

        request.setAttribute("prodottos", prodottos);
        request.setAttribute("search", search);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/result/categoria.jsp");
        dispatcher.forward(request,response);

    }
}
