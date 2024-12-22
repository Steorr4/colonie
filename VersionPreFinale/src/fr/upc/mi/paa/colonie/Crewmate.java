package fr.upc.mi.paa.colonie;

import java.util.ArrayList;
import java.util.List;

/**
 * Represente un colon ainsi que les informations le concernant
 */
public class Crewmate implements Comparable<Crewmate>{
    private String name;
    private boolean isEnvious;
    private Ressource rAssigned;
    private List<Ressource> preferences;
    private List<Crewmate> relations;

    /**
     * Constructeur d'un colon
     *
     * @param name le nom du colon
     */
    public Crewmate(String name){
        this.name = name;
        isEnvious = false;
        rAssigned = null;
        preferences = new ArrayList<>();
        relations = new ArrayList<>();
    }

    public void addRelation(Crewmate c){
        relations.add(c);
    }
    //Getters & Setters

    /**
     * Recupere le nom du colon.
     *
     * @return String contenant nom du colon
     */
    public String getName() {
        return name;
    }

    /**
     * Recupere si le colon est envieux.
     *
     * @return booleen vrai si oui, faux sinon
     */
    public boolean isEnvious() {
        return isEnvious;
    }

    /**
     * Met a jour son humeur
     *
     * @param envious booleen de s'il est envieux ou pas
     */
    public void setEnvious(boolean envious) {
        isEnvious = envious;
    }

    /**
     * Recupere la ressource associe a ce colon.
     *
     * @return la ressource qui lui est associee
     */
    public Ressource getrAssigned() {
        return rAssigned;
    }

    /**
     * Associe une ressource à ce colon.
     *
     * @param rAssigned une ressource
     */
    public void setrAssigned(Ressource rAssigned) {
        this.rAssigned = rAssigned;
    }

    /**
     * Recupere les preferences de ressources du colon.
     *
     * @return la liste de ses ressources preferees
     */
    public List<Ressource> getPreferences() {
        return preferences;
    }

    /**
     * Definie l'ordre de preference des ressources que le colon souhaite.
     *
     * @param preferences la liste de ses ressources preferees
     */
    public void setPreferences(List<Ressource> preferences) {
        this.preferences = preferences;
    }

    /**
     * Recupere les relations du colon
     *
     * @return la liste des colons que ce colon n'apprecie pas
     */
    public List<Crewmate> getRelations() {
        return relations;
    }

    //toString
    @Override
    public String toString() {
        return "Crewmate{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Crewmate o){
        return Integer.compare(o.getRelations().size(),this.getRelations().size() );
    }
   
}
