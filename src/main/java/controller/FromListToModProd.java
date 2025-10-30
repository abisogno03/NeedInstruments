package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Prodotto;
import model.Utente;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@MultipartConfig
@WebServlet("/from-list-to-mod-prod")
public class FromListToModProd extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/admin/aggiornaProdotto.jsp";
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }


        int id = Integer.parseInt(request.getParameter("id"));
        String marca = request.getParameter("marca");
        String modello = request.getParameter("modello");
        String strumento = request.getParameter("strumento");
        String tipo = request.getParameter("tipo");
        double prezzo = Double.parseDouble(request.getParameter("prezzo"));
        double sconto = Double.parseDouble(request.getParameter("sconto"));
        int quantita = Integer.parseInt(request.getParameter("quantita"));
        String image = "";

        Part filePart = request.getPart("image");
        if (filePart != null) {
            // Recupera il nome del file caricato
            image = getFileName(filePart);
            response.getWriter().println("Nome del file caricato: " + image);
        } else {
            response.getWriter().println("Nessun file caricato");
        }

        // Ottieni il percorso assoluto del file JSON
        String jsonFilePath = getServletContext().getRealPath("/prodotto.json");

        // Leggi il file JSON e ottieni l'oggetto JSON
        JSONObject productTypes;
        try {
            productTypes = readJsonFile(jsonFilePath);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Prodotto prodotto = new Prodotto(id, marca, modello, prezzo, sconto, quantita, strumento, tipo, image);

        request.setAttribute("jsonProductTypes", productTypes);
        request.setAttribute("prodotto", prodotto);
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    // Metodo per leggere il file JSON e ritornare un oggetto JSON
    private JSONObject readJsonFile(String jsonFilePath) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        try (Reader reader = new FileReader(jsonFilePath, StandardCharsets.UTF_8)) {
            return (JSONObject) jsonParser.parse(reader);
        }
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
