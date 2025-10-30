package model;

public class Prodotto {
    int id;
    String marca;
    String modello;
    double prezzo;
    double sconto;
    int quantita;
    String strumento;
    String tipo;
    String image;

    public Prodotto(){}

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getStrumento() {
        return strumento;
    }

    public void setStrumento(String strumento) {
        this.strumento = strumento;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModello() {
        return modello;
    }

    public void setModello(String modello) {
        this.modello = modello;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public double getSconto() {
        return sconto;
    }

    public void setSconto(double sconto) {
        this.sconto = sconto;
    }


    public Prodotto(int id, String marca, String modello, double prezzo, double sconto, int quantita, String strumento, String tipo, String image){
        this.id = id;
        this.marca = marca;
        this.modello = modello;
        this.prezzo = prezzo;
        this.sconto = sconto;
        this.strumento = strumento;
        this.quantita = quantita;
        this.tipo = tipo;
        this.image = image;
    }

}
