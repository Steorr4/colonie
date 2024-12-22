package fr.upc.mi.paa.colonie;

/**
 * Represente les differentes ressources ainsi que les infomations les concernant
 */
public class Ressource  {
    private String name;
    private boolean isAvailable;


    /**
     * Constructeur d'une name.
     *
     * @param ressource une chaine de caracteres permettant d'identifier la name
     */
    public Ressource(String ressource){
        this.name = ressource;
        isAvailable = true;

    }


    //Getters & Setters

    /**
     * Recupere la disponibilite d'un name ou non.
     *
     * @return un booleen indiquant vrai si la name est disponible et faux sinon
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Definie la disponibilite d'une name.
     *
     * @param available un booleen egal a vrai si la name est disponible et faux sinon
     */
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    /**
     * Recupere le proprietaire de la name.
     *
     * @return le colon possedant la name s'il y en a un
     */


    public String getName() { return name;}


    //toString
    @Override
    public String toString() {
        return "Ressource{" +
                "rscName=" + name +
                '}';
    }
    
}
