package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Carrello {
    int id;
    List<Prodotto> prodotti;
    Utente utente;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public List<Prodotto> getProdotti() {
        return prodotti;
    }

    public void setProdotti(List<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }

    public Carrello(){}

    public Carrello(List<Prodotto> prodotti, Utente utente){
        this.prodotti = prodotti;
        this.utente = utente;
    }
}
