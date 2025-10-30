package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UtenteDAO {
    ResultSet rs;

    public List<Utente> doRetrieveAll(){
        List<Utente> us = new ArrayList<>();
        try (Connection con = Logger.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM utente");

            ResultSet rs = ps.executeQuery();
            while(rs.next()){

                String username = rs.getString("username");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String email = rs.getString("email");
                String password = rs.getString("password");
                Date data_nascita = rs.getDate("data_nascita");
                boolean admin = rs.getBoolean("admin");

                Utente u = new Utente(username,nome,cognome,email,password,data_nascita,admin);
                us.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return us;
    }

    public Utente doRetrieveByUsername(String username) {
        try (Connection con = Logger.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM utente WHERE username=?");
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                Utente u = new Utente();
                u.setUsername(rs.getString(1));
                u.setNome(rs.getString(2));
                u.setCognome(rs.getString(3));
                u.setEmail(rs.getString(4));
                u.setPassword(rs.getString(5));
                u.setData_nascita(rs.getDate(6));
                u.setAdmin(rs.getBoolean(7));

                return u;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Utente doRetrieveByUsernamePassword(String username, String password){
        try (Connection con = Logger.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM utente WHERE username=? AND password=SHA1(?)");
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                Utente u = new Utente();
                u.setUsername(rs.getString("username"));
                u.setNome(rs.getString("nome"));
                u.setCognome(rs.getString("cognome"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setData_nascita(rs.getDate("data_nascita"));
                u.setAdmin(rs.getBoolean("admin"));
                System.out.println("User trovato: " + u.getUsername());

                return u;
            }
            else {
                System.out.println("Non esistono utenti con queste credenziali");
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doUpdateUtente(Utente u){
        try (Connection con = Logger.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("UPDATE utente SET nome=?, cognome=?, data_nascita=?, email=? WHERE username=?");

            ps.setString(1,u.getNome());
            ps.setString(2,u.getCognome());

            java.sql.Date sqlDate = new java.sql.Date(u.getData_nascita().getTime());
            ps.setDate(3, sqlDate);

            ps.setString(4, u.getEmail());

            ps.setString(5, u.getUsername());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUtenteByUsername(String username) {
        try (Connection con = Logger.getConnection()) {
            PreparedStatement ps1 = con.prepareStatement("DELETE FROM carrello WHERE username = ?");
            ps1.setString(1, username);
            ps1.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement("DELETE FROM preferiti WHERE username = ?");
            ps2.setString(1, username);
            ps2.executeUpdate();

            PreparedStatement ps3 = con.prepareStatement("DELETE FROM acquisto WHERE username = ?");
            ps3.setString(1, username);
            ps3.executeUpdate();

            PreparedStatement ps = con.prepareStatement("DELETE FROM utente WHERE username = ?");
            ps.setString(1, username);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No user found with the given username.");
            } else {
                System.out.println("User removed successfully.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doSave(Utente u) {
        try (Connection con = Logger.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO utente (username, nome, cognome, email, password, data_nascita, admin) VALUES(?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getNome());
            ps.setString(3, u.getCognome());
            ps.setString(4, u.getEmail());
            ps.setString(5,u.getPassword());
            ps.setDate(6, new java.sql.Date(u.getData_nascita().getTime()));
            ps.setBoolean(7,u.isAdmin());
            System.out.println("doSave OK");
            if (ps.executeUpdate() != 1) {
                System.out.println("doSave exception 1");
                throw new RuntimeException("INSERT error.");
            }
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

        } catch (SQLException e) {
            System.out.println("doSave exception 2");
            throw new RuntimeException(e);
        }
    }

    public String getPasswordByUsername(String username) {
        String password = null;
        try (Connection con = Logger.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT password FROM utente WHERE username = ?");
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                password = rs.getString("password");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return password;
    }

    public void updatePassword(String username, String newPassword) {
        try (Connection con = Logger.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE utente SET password = ? WHERE username = ?");
            ps.setString(1, newPassword);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
