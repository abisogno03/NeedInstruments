package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AcquistoDAO {
    ResultSet rs;

    public void doPurchase(Acquisto acquisto) {

        try (Connection con = Logger.getConnection()){
             PreparedStatement psAcquisto = con.prepareStatement("INSERT INTO acquisto " +
                     "(id_prodotto, username, data, numero) VALUES (?, ?, ?, ?)");
             PreparedStatement psUpdateQuantita = con.prepareStatement("UPDATE prodotto " +
                     "SET quantita = quantita - ? WHERE id = ?");



            // Inserisci l'acquisto
            psAcquisto.setInt(1, acquisto.getProdotto().getId());
            psAcquisto.setString(2, acquisto.getUtente().getUsername());
            java.sql.Date sqlDate = new java.sql.Date(acquisto.getData().getTime());
            psAcquisto.setDate(3, sqlDate);
            psAcquisto.setInt(4, acquisto.getNumero());
            psAcquisto.executeUpdate();

            // Aggiorno la quantita del prodotto nel DB
            psUpdateQuantita.setInt(1, acquisto.getNumero());
            psUpdateQuantita.setInt(2, acquisto.getProdotto().getId());
            psUpdateQuantita.executeUpdate();

            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Acquisto> doRetrieveAll(){
        try (Connection con = Logger.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM acquisto");
            rs = ps.executeQuery();
            List<Acquisto> pds = new ArrayList<>();
            while(rs.next()){

                int id_acquisto = rs.getInt("id_acquisto");
                int id_prodotto = rs.getInt("id_prodotto");
                String username = rs.getString("username");
                Date data = rs.getDate("data");
                int numero = rs.getInt("numero");

                Prodotto p = new Prodotto();
                p.setId(id_prodotto);

                Utente u = new Utente();
                u.setUsername(username);

                Acquisto a = new Acquisto(id_acquisto, p, u, data, numero);
                pds.add(a);
            }
            return pds;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Acquisto> doRetrieveByUtente(Utente utente) {
        List<Acquisto> listaProdotti = new ArrayList<>();

        if (utente == null) {
            return null; // Utente non trovato
        }

        try (Connection con = Logger.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM acquisto WHERE username=?");
            ps.setString(1, utente.getUsername());
            ResultSet rs = ps.executeQuery();

            ProdottoDAO prodottoDAO = new ProdottoDAO();
            while (rs.next()) {
                int id_acquisto = rs.getInt("id_acquisto");
                int id_prodotto = rs.getInt("id_prodotto");
                String username = rs.getString("username");
                Date data = rs.getDate("data");
                int numero = rs.getInt("numero");

                Prodotto p = new Prodotto();
                p.setId(id_prodotto);

                Acquisto a = new Acquisto(id_acquisto, p, utente, data, numero);
                listaProdotti.add(a);
            }
            return listaProdotti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
