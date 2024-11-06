package fr.upc.mi.colonie;

import java.util.ArrayList;
import java.util.List;

public class Crewmate {
    private String name;
    private boolean isEnvious;
    private Ressource rAssigned;
    private List<Ressource> preferences;
    private List<Crewmate> relations;

    public Crewmate(String name){
        this.name = name;
        isEnvious = false;
        rAssigned = null;
        preferences = new ArrayList<>();
        relations = new ArrayList<>();
    }


    //Getters & Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnvious() {
        return isEnvious;
    }

    public void setEnvious(boolean envious) {
        isEnvious = envious;
    }

    public Ressource getrAssigned() {
        return rAssigned;
    }

    public void setrAssigned(Ressource rAssigned) {
        this.rAssigned = rAssigned;
    }

    public List<Ressource> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<Ressource> preferences) {
        this.preferences = preferences;
    }

    public List<Crewmate> getRelations() {
        return relations;
    }

    public void setRelations(List<Crewmate> relations) {
        this.relations = relations;
    }

    //toString

    @Override
    public String toString() {
        return "Crewmate{" +
                "name='" + name + '\'' +
                '}';
    }
}
