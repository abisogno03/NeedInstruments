package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Prodotto;
import model.ProdottoDAO;

import java.io.IOException;

@MultipartConfig
@WebServlet("/update-prod-servlet")
public class UpdateProdServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/prodResults/prodottoAggiornatoOK.jsp";
        String errorAddress = "/WEB-INF/prodResults/prodottoNonTrovato.jsp";  // Pagina di errore

        int id = Integer.parseInt(request.getParameter("id"));
        String marca = request.getParameter("marca");
        String modello = request.getParameter("modello");
        double prezzo = Double.parseDouble(request.getParameter("prezzo"));
        double sconto = Double.parseDouble(request.getParameter("sconto"));
        int quantita = Integer.parseInt(request.getParameter("quantita"));
        String strumento = request.getParameter("strumento");
        String tipo = request.getParameter("tipo");
        String image = "";

        Part filePart = request.getPart("image");
        if (filePart != null && filePart.getSize() > 0) {
            image = getFileName(filePart);
        }

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        Prodotto productToUpdate = prodottoDAO.doRetrieveById(id);

        if (productToUpdate == null) {
            request.getRequestDispatcher(errorAddress).forward(request, response);
            return;
        }

        productToUpdate.setMarca(marca);
        productToUpdate.setModello(modello);
        productToUpdate.setQuantita(quantita);
        productToUpdate.setPrezzo(prezzo);
        productToUpdate.setSconto(sconto);
        productToUpdate.setImage(image);
        productToUpdate.setTipo(tipo);
        productToUpdate.setStrumento(strumento);

        prodottoDAO.doUpdateProdotto(productToUpdate);
        request.getRequestDispatcher(address).forward(request, response);

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
