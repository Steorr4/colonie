package fr.upc.mi.colonie;

public class Ressource {
    private int numero;
    private boolean isAvailable;
    private Crewmate proprietaire;

    public Ressource(int numero){
        this.numero = numero;
        isAvailable = false;
        proprietaire = null;
    }


    //Getters & Setters

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Crewmate getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(Crewmate proprietaire) {
        this.proprietaire = proprietaire;
    }
}
