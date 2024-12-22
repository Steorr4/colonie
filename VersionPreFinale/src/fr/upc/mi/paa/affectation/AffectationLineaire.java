package fr.upc.mi.paa.affectation;

import fr.upc.mi.paa.colonie.*;

public class AffectationLineaire implements AffectationStrategy {

	/**
	 * Methode d'affectation de ressources lineaire ou chaque colon obtient sa ressource preferee si disponible sinon la prochaine
	 * @param colonie
	 */
    @Override
    public void affecterRessources(Colonie colonie) throws Exception {
       
    	
    	for (Crewmate cr : colonie.getCrewmateList()) {//initialise l'affectation des ressources
    		 cr.setrAssigned(null);
    	 }
    	 for(Ressource r: colonie.getRessourceList()) {//met toutes les ressources disponibles
    		 r.setAvailable(true);
    	 }
        for (Crewmate c : colonie.getCrewmateList()) {
            for(Ressource r : c.getPreferences()){
                if(r.isAvailable()) {
                    c.setrAssigned(r);
                    r.setAvailable(false);
                    break;
                }
            }
        }
        colonie.setEnviousList();
    }
}
