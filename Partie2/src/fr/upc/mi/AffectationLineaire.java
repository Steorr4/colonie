package fr.upc.mi;

public class AffectationLineaire implements AffectationStrategy{
    @Override
    public void affecterRessources(Colonie colonie) throws Exception {
        for (Crewmate cr : colonie.getCrewmateList()) {
    		 cr.setrAssigned(null);
    	 }
    	 for(Ressource r: colonie.getRessourceList()) {
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
