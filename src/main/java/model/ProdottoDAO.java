package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ProdottoDAO {
    ResultSet rs;

    public List<Prodotto> doRetrieveBySearch(String search) {
        List<Prodotto> prodotti = new ArrayList<>();
        String[] searchTerms = search.split("\\s+");
        StringBuilder query = new StringBuilder("SELECT * FROM prodotto WHERE ");
        for (int i = 0; i < searchTerms.length; i++) {
            query.append("(LOWER(marca) LIKE ? OR LOWER(modello) LIKE ? " +
                    "OR LOWER(strumento) LIKE ? OR LOWER(tipo) LIKE ?)");
            if (i < searchTerms.length - 1) {
                query.append(" AND ");
            }
        }

        try (Connection con = Logger.getConnection();
             PreparedStatement ps = con.prepareStatement(query.toString())) {
            int parameterIndex = 1;
            for (String term : searchTerms) {
                String searchPattern = "%" + term.toLowerCase() + "%";
                ps.setString(parameterIndex++, searchPattern);
                ps.setString(parameterIndex++, searchPattern);
                ps.setString(parameterIndex++, searchPattern);
                ps.setString(parameterIndex++, searchPattern);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Prodotto p = new Prodotto();
                p.setId(rs.getInt("id"));
                p.setMarca(rs.getString("marca"));
                p.setModello(rs.getString("modello"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setSconto(rs.getDouble("sconto"));
                p.setQuantita(rs.getInt("quantita"));
                p.setStrumento(rs.getString("strumento"));
                p.setTipo(rs.getString("tipo"));
                p.setImage(rs.getString("image"));
                prodotti.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prodotti;
    }

    public List<Prodotto> doRetrieveByStrumentoTipo(String strumento, String tipo) {
        try (Connection con = Logger.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM prodotto WHERE strumento=? AND tipo=?");
            ps.setString(1, strumento);
            ps.setString(2, tipo);
            rs = ps.executeQuery();
            List<Prodotto> prodottos = new ArrayList<>();

            while (rs.next()) {
                Prodotto p = new Prodotto();
                p.setId(rs.getInt("id"));
                p.setMarca(rs.getString("marca"));
                p.setSconto(rs.getDouble("sconto"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setQuantita(rs.getInt("quantita"));
                p.setModello(rs.getString("modello"));
                p.setStrumento(rs.getString("strumento"));
                p.setTipo(rs.getString("tipo"));
                p.setImage(rs.getString("image"));

                prodottos.add(p);
            }
            return prodottos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Prodotto> doRetrieveByStrumento(String strumento) {
        try (Connection con = Logger.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM prodotto WHERE strumento=?");
            ps.setString(1, strumento);
            rs = ps.executeQuery();
            List<Prodotto> prodottos = new ArrayList<>();

            while (rs.next()) {
                Prodotto p = new Prodotto();
                p.setId(rs.getInt("id"));
                p.setMarca(rs.getString("marca"));
                p.setSconto(rs.getDouble("sconto"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setQuantita(rs.getInt("quantita"));
                p.setModello(rs.getString("modello"));
                p.setStrumento(rs.getString("strumento"));
                p.setTipo(rs.getString("tipo"));
                p.setImage(rs.getString("image"));

                prodottos.add(p);
            }
            return prodottos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Prodotto> getTopSoldProducts() {

        try (Connection con = Logger.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT p.*, COUNT(a.id_prodotto) AS sold_count " +
                    "FROM prodotto p JOIN acquisto a ON p.id = a.id_prodotto " +
                    "GROUP BY p.id " +
                    "ORDER BY sold_count DESC " +
                    "LIMIT 4");
            rs = ps.executeQuery();
            List<Prodotto> prodotti = new ArrayList<>();

            while (rs.next()) {
                Prodotto p = new Prodotto();
                p.setId(rs.getInt("id"));
                p.setMarca(rs.getString("marca"));
                p.setModello(rs.getString("modello"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setSconto(rs.getDouble("sconto"));
                p.setQuantita(rs.getInt("quantita"));
                p.setStrumento(rs.getString("strumento"));
                p.setTipo(rs.getString("tipo"));
                p.setImage(rs.getString("image"));
                prodotti.add(p);
            }
            return prodotti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addProdotto(Prodotto prodotto) {
        String sql = "INSERT INTO prodotto (marca, modello, prezzo, quantita, strumento, tipo, image) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Logger.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, prodotto.getMarca());
            ps.setString(2, prodotto.getModello());
            ps.setDouble(3, prodotto.getPrezzo());
            ps.setInt(4, prodotto.getQuantita());
            ps.setString(5, prodotto.getStrumento());
            ps.setString(6, prodotto.getTipo());
            ps.setString(7, prodotto.getImage());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Prodotto doRetrieveById(int id) {
        try (Connection con = Logger.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM prodotto WHERE id=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Prodotto p = new Prodotto();
                p.setId(rs.getInt("id"));
                p.setMarca(rs.getString("marca"));
                p.setModello(rs.getString("modello"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setSconto(rs.getDouble("sconto"));
                p.setQuantita(rs.getInt("quantita"));
                p.setStrumento(rs.getString("strumento"));
                p.setTipo(rs.getString("tipo"));
                p.setImage(rs.getString("image"));

                return p;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doUpdateProdotto(Prodotto p){
        try (Connection con = Logger.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("UPDATE prodotto SET marca=?, modello=?, prezzo=?, " +
                            "sconto=?, quantita=?, strumento=?, tipo=?, image=? WHERE id=?");

            ps.setString(1,p.getMarca());
            ps.setString(2,p.getModello());
            ps.setDouble(3, p.getPrezzo());
            ps.setDouble(4, p.getSconto());
            ps.setInt(5, p.getQuantita());
            ps.setString(6, p.getStrumento());
            ps.setString(7, p.getTipo());
            ps.setString(8, p.getImage());
            ps.setInt(9, p.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeProdottoById(int id){
        try (Connection con = Logger.getConnection()) {
            PreparedStatement ps1 = con.prepareStatement("DELETE FROM carrello WHERE id_prodotto = ?");
            ps1.setInt(1, id);
            ps1.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement("DELETE FROM preferiti WHERE id_prodotto = ?");
            ps2.setInt(1, id);
            ps2.executeUpdate();

            PreparedStatement ps3 = con.prepareStatement("DELETE FROM acquisto WHERE id_prodotto = ?");
            ps3.setInt(1, id);
            ps3.executeUpdate();


            PreparedStatement ps =
                    con.prepareStatement("DELETE FROM prodotto WHERE id=?");

            ps.setInt(1,id);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("No rows affected, user not found.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Prodotto> doRetrieveAll(){
        try (Connection con = Logger.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM prodotto");
            rs = ps.executeQuery();
            List<Prodotto> pds = new ArrayList<>();
            while(rs.next()){

                int id = rs.getInt("id");
                String marca = rs.getString("marca");
                String modello = rs.getString("modello");
                double prezzo = rs.getDouble("prezzo");
                double sconto = rs.getDouble("sconto");
                int quantita = rs.getInt("quantita");
                String strumento = rs.getString("strumento");
                String tipo = rs.getString("tipo");
                String image = rs.getString("image");

                Prodotto p = new Prodotto(id, marca, modello, prezzo, sconto, quantita, strumento, tipo, image);
                pds.add(p);
            }
            return pds;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Prodotto> getLastSoldProduct() {
        try (Connection con = Logger.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM acquisto a JOIN prodotto p ON a.id_prodotto = p.id ORDER BY a.data DESC"
            );
            rs = ps.executeQuery();
            Map<Integer, Prodotto> productMap = new HashMap<>();

            while (rs.next()) {
                int idProdotto = rs.getInt("id");
                if (!productMap.containsKey(idProdotto)) {
                    Prodotto prodotto = new Prodotto();
                    prodotto.setId(rs.getInt("id"));
                    prodotto.setMarca(rs.getString("marca"));
                    prodotto.setModello(rs.getString("modello"));
                    prodotto.setStrumento(rs.getString("strumento"));
                    prodotto.setTipo(rs.getString("tipo"));
                    prodotto.setPrezzo(rs.getDouble("prezzo"));
                    prodotto.setSconto(rs.getDouble("sconto"));
                    prodotto.setQuantita(rs.getInt("quantita"));
                    prodotto.setImage(rs.getString("image"));

                    productMap.put(idProdotto, prodotto);
                }
            }

            return new ArrayList<>(productMap.values());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Prodotto> getOfferte(){
        try (Connection con = Logger.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM prodotto WHERE sconto!=0");
            rs = ps.executeQuery();
            List<Prodotto> pds = new ArrayList<>();
            while(rs.next()){

                int id = rs.getInt("id");
                String marca = rs.getString("marca");
                String modello = rs.getString("modello");
                double prezzo = rs.getDouble("prezzo");
                double sconto = rs.getDouble("sconto");
                int quantita = rs.getInt("quantita");
                String strumento = rs.getString("strumento");
                String tipo = rs.getString("tipo");
                String image = rs.getString("image");

                Prodotto p = new Prodotto(id, marca, modello, prezzo, sconto, quantita, strumento, tipo, image);
                pds.add(p);
            }
            return pds;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProductQuantity(int productId, int newQuantity) {
        String query = "UPDATE prodotto SET quantita = ? WHERE id = ?";

        try (Connection con = Logger.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, newQuantity);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void increaseProductSoldCount(int productId, int soldCount) {
        String query = "UPDATE prodotto SET venduti = venduti + ? WHERE id = ?";

        try (Connection con = Logger.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, soldCount);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
