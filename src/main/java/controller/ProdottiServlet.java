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

@WebServlet("/prodotti-servlet")
public class ProdottiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/product/navVerSearch.jsp";

        String strumento = request.getParameter("strumento");
        String tipo = request.getParameter("tipo");
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        List<Prodotto> prodotti;

        if(tipo != null){
            prodotti = prodottoDAO.doRetrieveByStrumentoTipo(strumento, tipo);
        }
        else{
            prodotti = prodottoDAO.doRetrieveByStrumento(strumento);
        }

        request.setAttribute("strumento", strumento);
        request.setAttribute("tipo", tipo);
        request.setAttribute("prodotti", prodotti);
        request.getRequestDispatcher(address).forward(request, response);
    }
}
