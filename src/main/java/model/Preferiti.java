package model;

import java.util.List;

public class Preferiti {
    int id;
    private List<Prodotto> lista;
    private Utente utente;

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

    public List<Prodotto> getLista() {
        return lista;
    }

    public void setLista(List<Prodotto> lista) {
        this.lista = lista;
    }

    public Preferiti() {}

    public Preferiti(List<Prodotto> lista, Utente utente) {
        this.lista = lista;
        this.utente = utente;
    }
}
