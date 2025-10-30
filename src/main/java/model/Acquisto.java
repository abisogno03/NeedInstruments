package model;

import java.util.Date;

public class Acquisto {
    int id_acquisto;
    Prodotto prodotto;
    Utente utente;
    int numero;
    Date data;

    public Acquisto(){}

    public Acquisto(int id_acquisto, Prodotto prodotto, Utente utente, int numero){
        this.id_acquisto = id_acquisto;
        this.prodotto = prodotto;
        this.utente = utente;
        this.numero = numero;
        data = new Date();
    }

    public Acquisto(Prodotto prodotto, Utente utente, int numero){
        this.prodotto = prodotto;
        this.utente = utente;
        this.numero = numero;
        data = new Date();
    }

    public Acquisto(int id_acquisto, Prodotto prodotto, Utente utente, Date data, int numero){
        this.id_acquisto = id_acquisto;
        this.prodotto = prodotto;
        this.utente = utente;
        this.numero = numero;
        this.data = data;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Utente getUtente() {
        return utente;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
