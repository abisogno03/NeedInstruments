package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;


@WebServlet("/new-prod-form")
public class NewProdForm extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Ottieni il percorso assoluto del file JSON
        String jsonFilePath = getServletContext().getRealPath("/prodotto.json");

        // Leggi il file JSON e ottieni l'oggetto JSON
        JSONObject productTypes;
        try {
            productTypes = readJsonFile(jsonFilePath);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // Setta l'oggetto JSON come attributo nella richiesta
        request.setAttribute("jsonProductTypes", productTypes);

        // Inoltra la richiesta alla JSP per l'aggiunta del prodotto
        String address = "/WEB-INF/admin/aggiungiProdotto.jsp";
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
}
