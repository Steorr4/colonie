package fr.upc.mi;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class AffectationBruteForce implements AffectationStrategy{
	/**
	 * methode d'affectation de ressources de la colonie style Brute Force ou la liste de colons est melangée aleatoirement
	 * et chaque colon obtient sa ressource preferee si disponible sinon la prochaine a chaque iteration
	 * @param colonie 
	 */
	@Override
	public void affecterRessources(Colonie colonie) {
        // Étape 1 : Initialisation
		
    	// Map pour sauvegarder la meilleure solution (nom du colon -> nom de la ressource)
		int maxIterations=2* colonie.getCrewmateList().size();
        HashMap<String, String> meilleureAffectation = new HashMap<>();
        
        Collections.sort(colonie.getCrewmateList()); // Trier la liste initialement
        List<Crewmate> meilleureListe = new ArrayList<>(colonie.getCrewmateList()); 
        
       algoAffectation(colonie,meilleureListe); // Application de l'affectation sur la liste triée
        
        int meilleurNbJaloux = colonie.nbEnvious();
        
        colonie.sauvegarderAffectation(meilleureAffectation); // Sauvegarde initiale
        
        List<Crewmate> crewmateList=colonie.getCrewmateList();
        
        // Étape 2 : Itérations avec ordres aléatoires
        for (int i = 0; i < maxIterations; i++) {
            // Mélanger aléatoirement la liste des colons
            Collections.shuffle(colonie.getCrewmateList());

            // Appliquer l'algorithme d'affectation
            algoAffectation(colonie,colonie.getCrewmateList());
            int nbJalouxCourant = colonie.nbEnvious();
            
            
            
            // Comparer et mettre à jour si nécessaire
            if (nbJalouxCourant < meilleurNbJaloux) {
                meilleurNbJaloux = nbJalouxCourant;
                meilleureListe = new ArrayList<>(colonie.getCrewmateList());
                colonie.sauvegarderAffectation(meilleureAffectation); // Sauvegarde initiale// Sauvegarder la solution
                
            }

            // Rétablir la meilleure affectation pour éviter les biais
            crewmateList = new ArrayList<>(meilleureListe);
            colonie.restaurerAffectation(meilleureAffectation);
            
            //si le nombre de jaloux atteint le minimum possible 0 arreter 
            if(meilleurNbJaloux==0) break;
        }

        // Étape 3 : Retourner la meilleure solution trouvée
        
        crewmateList = new ArrayList<>(meilleureListe); // Restaurer la meilleure liste finale
        //afficher les affectations
        colonie.printAffectations();
        //afficher les jaloux
        colonie.printEnviousCrewmates();
        System.out.println("Meilleure affectation trouvée avec " + meilleurNbJaloux + " jaloux.");
        
    }
	  /**
     * Methode d'effectation naive qui affecte à chaqu'un des colons de
     * crewmateList dans l'ordre la ressource qu'il préfère si disponible sinon la prochaine.
     * Utile pour la methode algoAffectationOptimisee
     * @param colonie 
     * @param crewmateList une liste de crewmates
     *
     */
   public void algoAffectation(Colonie colonie,List<Crewmate> crewmateList ) {
   	 for (Crewmate cr : crewmateList) {
   		 cr.setrAssigned(null);
   	 }
   	 for(Ressource r:colonie.getRessourceList()) {
   		 r.setAvailable(true);
   	 }
       for (Crewmate c : crewmateList) {
       	
           //int nbJalouxApresAffectation = nbJaloux;
           
           for (Ressource r : c.getPreferences()) {
           	
               if (r.isAvailable()) {
                   c.setrAssigned(r);
                   r.setAvailable(false);
                   colonie.setEnviousList();
         
                       break;
                   
               }
           }
           
       }
      
   }

}
