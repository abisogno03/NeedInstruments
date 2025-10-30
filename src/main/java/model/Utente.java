package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class Utente {
    String username;
    String nome;
    String cognome;
    String email;
    String password;
    Date data_nascita;
    boolean admin;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(password.getBytes(StandardCharsets.UTF_8));
            this.password = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public java.util.Date getData_nascita() {
        return data_nascita;
    }

    public void setData_nascita(Date data_nascita) {
        this.data_nascita = data_nascita;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Utente(){}

    public Utente(String username, String nome, String cognome, String email, String password, Date data_nascita, boolean admin){
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email = email;
        setPassword(password);
        this.data_nascita = data_nascita;
        this.admin = admin;
    }

    public Utente(String username, String nome, String cognome, String email, Date data_nascita, boolean admin){
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email = email;
        this.data_nascita = data_nascita;
        this.admin = admin;
    }

    public Utente(String username, String nome, String cognome, String email, String password, Date data_nascita){
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email = email;
        setPassword(password);
        this.data_nascita = data_nascita;
        this.admin = false;
    }

    public Utente(String username, String email, String password){
        this.username = username;
        setPassword(password);
        this.email = email;
    }

    // Metodo per ottenere la data formattata
    public String getDataNascitaFormatted() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(data_nascita);
    }
}
