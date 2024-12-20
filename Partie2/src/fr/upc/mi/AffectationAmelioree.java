package fr.upc.mi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class AffectationAmelioree implements AffectationStrategy{
	@Override
    public void affecterRessources(Colonie colonie) throws Exception {
        // Trier la liste des membres d'équipage par préférence de ressources
        Collections.sort(colonie.getCrewmateList());

        // Appliquer une première affectation automatique
        colonie.affectationAuto();

        // Répéter le processus pour un nombre défini d'itérations
        int maxIterations = 1000;
        HashMap<String, String> meilleureAffectation = new HashMap<>();
        List<Crewmate> meilleureListe = new ArrayList<>(colonie.getCrewmateList());
        int meilleurNbJaloux = colonie.nbEnvious();
        colonie.sauvegarderAffectation(meilleureAffectation);
        List<Crewmate> crewmateList=colonie.getCrewmateList();
        List<Crewmate> envieux;
        int nbIterationsSansAmelioration=0;

        for (int i = 0; i < maxIterations; i++) {
        	envieux = crewmateList.stream().filter(Crewmate::isEnvious).collect(Collectors.toList());
            if (envieux.isEmpty()) break;

            //Crewmate pirate = envieux.get(generateRandomNumber(envieux.size()));
            Crewmate pirate=crewmateList.get(Colonie.generateRandomNumber(crewmateList.size()));
            Crewmate voisin;
            do {
                voisin = crewmateList.get(Colonie.generateRandomNumber(crewmateList.size()));
            } while (voisin == pirate);

            colonie.swapRessources(pirate, voisin);

            int nbJalouxCourant = colonie.nbEnvious();
            if (nbJalouxCourant < meilleurNbJaloux) {
                meilleurNbJaloux = nbJalouxCourant;
                nbIterationsSansAmelioration = 0; 
                colonie.sauvegarderAffectation(meilleureAffectation);
          
            } 
            //si aucune amelioration au bout de 100 on remelange la liste
            else if(nbIterationsSansAmelioration>150) {
            	System.out.println("shuffle");
            	Collections.shuffle(crewmateList);
            	colonie.affectationAuto();
            	nbIterationsSansAmelioration = 0;
           }
            else {
            	nbIterationsSansAmelioration++;
            	colonie.swapRessources(pirate, voisin);// Annule le swap si non bénéfique
            }
            	
               colonie.restaurerAffectation(meilleureAffectation); 

            if (meilleurNbJaloux == 0) break;
        
        }

        // Affichage du résultat
        colonie.printAffectations();
        colonie.printEnviousCrewmates();
        System.out.println("Meilleure affectation trouvée avec " + meilleurNbJaloux + " jaloux.");
    }


}



