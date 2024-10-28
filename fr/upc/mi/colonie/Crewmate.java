package fr.upc.mi.colonie;

import java.util.ArrayList;
import java.util.List;

public class Crewmate {
    private String name;
    private List<Ressource> preferences;
    private List<Crewmate> relations;

    public Crewmate(String name){
        this.name = name;
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
}
