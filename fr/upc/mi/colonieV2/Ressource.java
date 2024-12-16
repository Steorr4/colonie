package fr.upc.mi.colonieV2;

/**
 * Represente les differentes ressources ainsi que les infomations les concernant
 */
public class Ressource {
    private String ressource;
    private boolean isAvailable;


    /**
     * Constructeur d'une ressource.
     *
     * @param ressource un entier permettant d'identifier la ressource
     */
    public Ressource(String ressource){
        this.ressource = ressource;
        isAvailable = true;

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





    //toString
    @Override
    public String toString() {
        return "Ressource{" +
                "rscName=" + ressource +
                '}';
    }
}
