package fr.upc.mi.paa.affectation;

import fr.upc.mi.paa.colonie.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class AffectationAmelioree implements AffectationStrategy {
	/**
	 * Methode qui implemente un algorithme "ameliore" de l'algorithme naif, celui-ci commence par trier la liste des colons
	 * selon leurs degres d'ennemis (le nb d'ennemis par colon) par ordre decroissant afin que les colons les plus problematiques
	 * soient traites en premiers, puis applique une affectation lineaire, prend au hasard deux colons distincts de la colonie et 
	 * echange leurs ressources si la solution actuelle est meilleure, elle est sauvegardee sinon retour à la precedente.
	 * Afin de ne pas bloquer dans des minimas locaux ici, on rajoute un critere en plus qui est le nombre d'affectations sans ameliorations
	 * si ce nombre est depasse la liste est alors melangee aleatoirement et ainsi de suite.
     *
	 * @param colonie 
	 */
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
            } while (voisin == pirate);//deux colons distincts

            colonie.swapRessources(pirate, voisin);

            int nbJalouxCourant = colonie.nbEnvious();
            if (nbJalouxCourant < meilleurNbJaloux) {
                meilleurNbJaloux = nbJalouxCourant;
                nbIterationsSansAmelioration = 0;  //remet à 0 le nombre d'iterations sans amelioration
                colonie.sauvegarderAffectation(meilleureAffectation);//sauvegardea de la solution
          
            } 
            //si aucune amelioration au bout de 150 on remélange la liste
            else if(nbIterationsSansAmelioration>150) {
            	
            	Collections.shuffle(crewmateList); //melange la liste
            	colonie.affectationAuto();
            	nbIterationsSansAmelioration = 0;
           }
            else {//si aucune amelioration 
            	nbIterationsSansAmelioration++;
            	colonie.swapRessources(pirate, voisin);// Annule le swap si non bénéfique
            }
            	
               colonie.restaurerAffectation(meilleureAffectation); 
               //arreter immediatement quand le minimum 0 est atteint
               if (meilleurNbJaloux == 0) break;
        
        }

        // Affichage du résultat
        colonie.printAffectations();
        colonie.printEnviousCrewmates();
        System.out.println("Meilleure affectation trouvée avec " + meilleurNbJaloux + " jaloux.");
    }


}



