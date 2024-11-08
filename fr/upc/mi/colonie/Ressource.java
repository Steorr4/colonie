package fr.upc.mi.colonie;

/**
 * Represente les differentes ressources ainsi que les infomations les concernant
 */
public class Ressource {
    private int numero;
    private boolean isAvailable;
    private Crewmate proprietaire;

    /**
     * Constructeur d'une ressource.
     *
     * @param numero un entier permettant d'identifier la ressource
     */
    public Ressource(int numero){
        this.numero = numero;
        isAvailable = true;
        proprietaire = null;
    }


    //Getters & Setters

    /**
     * Recupere la disponibilite d'un ressource ou non.
     *
     * @return un booleen indiquant vrai si la ressource est disponible et faux sinon
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Definie la disponibilite d'une ressource.
     *
     * @param available un booleen egal a vrai si la ressource est disponible et faux sinon
     */
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    /**
     * Recupere le proprietaire de la ressource.
     *
     * @return le colon possedant la ressource s'il y en a un
     */
    public Crewmate getProprietaire() {
        return proprietaire;
    }

    /**
     * Definie le proprietaire de la ressource.
     *
     * @param proprietaire le colon a qui on veut donner la ressource
     */
    public void setProprietaire(Crewmate proprietaire) {
        this.proprietaire = proprietaire;
    }

    //toString
    @Override
    public String toString() {
        return "Ressource{" +
                "numero=" + numero +
                '}';
    }
}
