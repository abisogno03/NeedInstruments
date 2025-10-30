package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Prodotto;
import model.ProdottoDAO;
import model.Utente;

import java.io.IOException;

@MultipartConfig
@WebServlet("/add-prod-servlet")
public class AddProdServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String address = "/WEB-INF/prodResults/prodottoAggiuntoOk.jsp";
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }


        String marca = request.getParameter("marca");
        String modello = request.getParameter("modello");
        double prezzo = Double.parseDouble(request.getParameter("prezzo"));
        int quantita = Integer.parseInt(request.getParameter("quantita"));
        String strumento = request.getParameter("strumento");
        String tipo = request.getParameter("tipo");
        String image = "";

        Part filePart = request.getPart("image");
        if (filePart != null) {
            // Recupera il nome del file caricato
            image = getFileName(filePart);
            response.getWriter().println("Nome del file caricato: " + image);
        } else {
            response.getWriter().println("Nessun file caricato");
        }

        Prodotto p = new Prodotto();
        p.setMarca(marca);
        p.setModello(modello);
        p.setPrezzo(prezzo);
        p.setQuantita(quantita);
        p.setStrumento(strumento);
        p.setTipo(tipo);
        p.setImage(image);

        ProdottoDAO pdao = new ProdottoDAO();
        pdao.addProdotto(p);
        System.out.println("Prodotto " + p.getMarca() + " " + p.getModello() + " aggiunto con successo!");

        request.setAttribute("prodotto", p);
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] elements = contentDisposition.split(";");
        for (String element : elements) {
            if (element.trim().startsWith("filename")) {
                return element.substring(element.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
