package fr.upc.mi.paa.colonie;

import fr.upc.mi.paa.affectation.*;

import java.io.*;
import java.util.*;

/**
 * Simule le partage de ressources dans une colonie en fonction des relations
 * des colons et de leurs affinites.
 */
public class Colonie  {
    private List<Crewmate> crewmateList; //List car on ne connait pas le nombre de colons intial et la manipulation des List (ajout) est plus simple
    private List<Ressource> ressourceList;
    private AffectationStrategy affectationStrategy;//utilisation du design pattern Strategy
    /**
     * Constructeur de la colonie.
     *
     * @param crewmateList la liste des colons
     * @param ressourceList la liste des ressources partageables
     */
    public Colonie(List<Crewmate> crewmateList, List<Ressource> ressourceList) {
        this.crewmateList = crewmateList;
        this.ressourceList = ressourceList;
    }
    
    /**
     * Methode d'effectation naive qui affecte à chaqu'un des colons de
     * crewmateList dans l'ordre la ressource qu'il préfère si disponible sinon la prochaine.
     *
     */

     public void affectationAuto() throws Exception{
    	 for (Crewmate cr : crewmateList) {
    		 cr.setrAssigned(null);
    	 }
    	 for(Ressource r:ressourceList) {
    		 r.setAvailable(true);
    	 }
        for (Crewmate c : crewmateList) {
            for(Ressource r : c.getPreferences()){
                if(r.isAvailable()) {
                    c.setrAssigned(r);
                    r.setAvailable(false);
                    break;
                }
            }
        }
        setEnviousList();
    }
    
   
    /**
     *  Méthode pour définir la stratégie à utiliser
     * @param affectationStrategy
     */
    public void setAffectationStrategy(AffectationStrategy affectationStrategy) {
        this.affectationStrategy = affectationStrategy;
    }

    /**
     *  Méthode pour appliquer la stratégie d'affectation
     * @throws Exception
     */
    public void appliquerAffectation() throws Exception {
        affectationStrategy.affecterRessources(this);
    }
    
    
   


    /**
     * Genere un nombre random entre ZÉRO et N exclu.
     * 
     * @param n la borne superieure exclu.
     */

    public static int generateRandomNumber(int n)
    {
        if (n < 0) {
            throw new IllegalArgumentException("n must not be negative");
        }

        return new Random().nextInt(n);
    }
    
    /**
     * Sauvegarde la solution actuelle
     * 
     * @param solution une hashmap qui contient le nom du colon et la ressource qu'on lui a affecté dans la solution.
     */
    public void sauvegarderAffectation(HashMap<String,String> solution) {
    	solution.clear();
        for (Crewmate c : crewmateList) {
            solution.put(c.getName(), c.getrAssigned().getName());
        }
    }
    
    
    /**
     * Restaure la liste crewmateList avec la solution.
     * 
     * @param solution une hashmap qui contient le nom du colon et la ressource qu'on lui a affecté dans la solution.
     */
    public void restaurerAffectation(HashMap<String,String> solution) {
    	for(Crewmate crewmate:crewmateList) {
    		Ressource r= Colonie.getRessource(solution.get(crewmate.getName()),ressourceList);
    		crewmate.setrAssigned(r);
    	}
    	setEnviousList();
    }
   
    /**
     * Affiche les affectations des ressources dans la colonie.
     */
    public void printAffectations() {
        for (Crewmate c : crewmateList) {
            System.out.println(c.getName() + " -> " +
                    (c.getrAssigned() != null ? c.getrAssigned().getName() : "Aucune ressource"));
        }
    }


    /**
     * Met à jour les etats des colons s'ils sont jaloux ou pas
     */
    
    public void setEnviousList(){ 
        for(Crewmate c : crewmateList){
            c.setEnvious(false);
        }
        for(Crewmate c : crewmateList){
            for(Ressource r : c.getPreferences()) {
                if(r.equals(c.getrAssigned())){
                    break;
                }
                for (Crewmate mechant : c.getRelations()) {
                    if (mechant.getrAssigned()==r){
                        c.setEnvious(true);
                        break;
                    }
                }
            }
        }
    }
    
    /**
     * Affiche les colons jaloux.
     */
    public void printEnviousCrewmates() {
        for (Crewmate c : crewmateList) {
            if (c.isEnvious()) {
                System.out.println(c.getName() + " est jaloux.");
            }
        }
    }
    /**
     * Permet un enchange direct des ressources entre deux colons.
     *
     * @param c1 le premier colon
     * @param c2 le deuxieme colon
     */
    public void swapRessources(Crewmate c1, Crewmate c2){
        Ressource r1 = c1.getrAssigned();
        Ressource r2 = c2.getrAssigned();

        c2.setrAssigned(r1);
        c1.setrAssigned(r2);

        setEnviousList();
    }

    /**
     * Parcours de la liste des colons pour recuperer le nombre d'envieux
     * au sein de la colonie.
     *
     * @return l'entier representant le nombre d'envieux
     */
    public int nbEnvious(){
        int cpt = 0;
        for(Crewmate c : crewmateList){
            if(c.isEnvious()) cpt++;
        }
        return cpt;
    }

     //Getters & Setters (Colonie)
    /**
     * Pour recuperer la liste des colons de la colonie.
     *
     * @return la liste des colons
     */
    public List<Crewmate> getCrewmateList() {
        return crewmateList;
    }
    public List<Ressource> getRessourceList() {
		
		return ressourceList;
	}
     /**
         * Permet d'ajouter une relation entre deux colons.
         *
         * @param c1 le premier colon
         * @param c2 le deuxieme colon
         */
     private static void setRelation(Crewmate c1, Crewmate c2){
         c1.getRelations().add(c2);
         c2.getRelations().add(c1);
     }
     
     /**
      * Enumeration des etats pour la verif de fichier.
      */

     public enum State{
         COLON,
         RESSOURCE,
         DETESTE,
         PREFERENCES
     }
     
     /**
      * Affectation des ressources selon le fichier sauvegarde de l'utilisateur
      * @param bw
      * @throws IOException
      * @throws Exception
      */
     public void save(BufferedWriter bw) {
    	 try {

  			 for(Crewmate c : crewmateList){
                   bw.write(c.getName()+":"+c.getrAssigned().getName());
                   bw.newLine();
             }

  		} catch (IOException e) {
  			System.err.println(e.getMessage());
  		}
     }
     
     /**
      * Recupere la ressource qui a le meme nom que nom
      * @param nom nom de la ressource rechecrhchée 
      * @param ressourceList la ressource si presente sinon null
      * @return la ressource si presente sinon null
      */
     public static Ressource getRessource(String nom, List<Ressource> ressourceList){
         for(Ressource r:ressourceList){
             if(r.getName().equals(nom)) return r;
         }
     return null;
     }
     

     /**
      * Recupere le colon qui a le meme nom que nom
      * @param nom nom du colon rechecrhche. 
      * @param crewmateList la ressource si presente sinon null
      * @return la colon si presente sinon null
      */
     public static Crewmate getCrewmate(String nom, List<Crewmate> crewmateList){
         for(Crewmate c:crewmateList){
             if(c.getName().equals(nom)) return c;
         }
     return null;
     }
     
     /**
      * 
      * @return la strategie d'affectation de la colonie
      */
     public AffectationStrategy getAffectationStrategy() {
 		return affectationStrategy;
 	}
	
}









