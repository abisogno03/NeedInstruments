package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PreferitiDAO {
    ResultSet rs;

    public Preferiti getFavoritesByUtente(String username) {
        try (Connection con = Logger.getConnection()) {
            List<Prodotto> listaProdotti = new ArrayList<>();
            UtenteDAO utenteDAO = new UtenteDAO();
            Utente utente = utenteDAO.doRetrieveByUsername(username);
            PreparedStatement ps = con.prepareStatement(
                    "SELECT p.* FROM prodotto p JOIN preferiti pr ON p.id = pr.id_prodotto WHERE pr.username = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();

            while (rs.next()) {
                Prodotto p = new Prodotto();
                p.setId(rs.getInt("id"));
                p.setMarca(rs.getString("marca"));
                p.setModello(rs.getString("modello"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setQuantita(rs.getInt("quantita"));
                p.setSconto(rs.getDouble("sconto"));
                p.setStrumento(rs.getString("strumento"));
                p.setTipo(rs.getString("tipo"));
                p.setImage(rs.getString("image"));

                listaProdotti.add(p);
            }

            return new Preferiti(listaProdotti, utente);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeFromPreferiti(String username, int id) {
        String sql = "DELETE FROM preferiti WHERE username = ? AND id_prodotto = ?";

        try (Connection con = Logger.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePreferiti(String username, int idProdotto) {
        String checkSql = "SELECT COUNT(*) FROM preferiti WHERE username = ? AND id_prodotto = ?";
        String insertSql = "INSERT INTO preferiti (username, id_prodotto) VALUES (?, ?)";

        try (Connection con = Logger.getConnection();
             PreparedStatement checkPs = con.prepareStatement(checkSql);
             PreparedStatement insertPs = con.prepareStatement(insertSql)) {

            checkPs.setString(1, username);
            checkPs.setInt(2, idProdotto);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next() && rs.getInt(1) == 0) { // Se il prodotto non è già nei preferiti
                insertPs.setString(1, username);
                insertPs.setInt(2, idProdotto);
                insertPs.executeUpdate();
            } else {
                throw new RuntimeException("Il prodotto è già presente nei preferiti");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
