package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarrelloDAO {
    ResultSet rs;

    public Carrello getCarrelloByUtente(String username) {
        List<Prodotto> listaProdotti = new ArrayList<>();
        UtenteDAO utenteDAO = new UtenteDAO();
        Utente utente = utenteDAO.doRetrieveByUsername(username);

        if (utente == null) {
            return null; // Utente non trovato
        }

        try (Connection con = Logger.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT id_prodotto FROM carrello WHERE username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            ProdottoDAO prodottoDAO = new ProdottoDAO();
            while (rs.next()) {
                int id = rs.getInt("id_prodotto");
                Prodotto prodotto = prodottoDAO.doRetrieveById(id);
                if (prodotto != null) {
                    listaProdotti.add(prodotto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return new Carrello(listaProdotti, utente);
    }

    public void updateCarrello(String username, int idProdotto) {
        try (Connection con = Logger.getConnection()) {
            // Preparare la query per inserire un prodotto nel carrello
            PreparedStatement ps = con.prepareStatement("INSERT INTO carrello (username, id_prodotto) VALUES (?, ?)");
            ps.setString(1, username);
            ps.setInt(2, idProdotto);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeFromCarrello(String username, int id) {
        try (Connection con = Logger.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM carrello WHERE username=? AND id_prodotto=?");
            ps.setString(1, username);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
