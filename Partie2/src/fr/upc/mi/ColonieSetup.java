package fr.upc.mi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class ColonieSetup {
   


    public Colonie setUp(String path) throws Exception {
            
                List<Crewmate> crewmateList = new ArrayList<>();
                List<Ressource> ressourceList = new ArrayList<>();
                BufferedReader br = new BufferedReader(new FileReader(path));
                boolean verifValide = verifFichier(path);
                String ligne=null;
                String separateur = "[(),.]";

                if(verifValide){
                    
                    while ((ligne = br.readLine()) != null) {
                        String[] l = ligne.split(separateur);
                        if (ligne.startsWith("colon")) {
                            crewmateList.add(new Crewmate(l[1]));
                        }
                        if (ligne.startsWith("ressource")) {

                            ressourceList.add(new Ressource(l[1]));
                        }
                        if (ligne.startsWith("deteste")) {

                            Crewmate c1 = Colonie.getCrewmate(l[1], crewmateList);
                            Crewmate c2 = Colonie.getCrewmate(l[2], crewmateList);
                            c1.addRelation(c2);
                            c2.addRelation(c1);
                        }
                        if (ligne.startsWith("preferences")) {

                            Crewmate c = Colonie.getCrewmate(l[1], crewmateList);
                            for (int i = 2; i < ressourceList.size()+2; i++) {
                            	for(Ressource r: ressourceList) {
                            		if(r.getRessource().equals(l[i])) {
                            			c.getPreferences().add(r);
                            		}
                            	}
                                
                            }
                        }
                    }


                    br.close();
                    return new Colonie(crewmateList, ressourceList);

                }

                else {
                	br.close();
                	throw new Exception("fichier de format invalide");
                }

                
            
        

    }



    public static boolean verifFichier(String path) {
        try {
        	File file = new File(path);

            // Vérifiez si le fichier existe
            if (!file.exists()) {
                throw new Exception("Le fichier spécifié est inexistant : " + path);
            }

            // Vérifiez si le fichier est vide
            if (file.length() == 0) {
                throw new Exception("Le fichier spécifié est vide : " + path);
            }
            
            List<String> nomColon = new ArrayList<>();
            List<String> nomRessources = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(path));
            String ligne = null;
            int nbColon = 0;
            int nbRessource = 0;
            int nbPreferences = 0;

            // Déclarez l'état en dehors de la boucle
            Colonie.State actualState = Colonie.State.COLON;
            int numeroLigne = 0;

            while ((ligne = br.readLine()) != null) {
                numeroLigne++;
                String separateur = "[(),.]";
                String[] split = ligne.split(separateur);

                if (!ligne.endsWith(").")) {
                    throw new Exception("La ligne " + numeroLigne + " ne finit pas avec un point");
                }

                if (ligne.startsWith("colon(")) {
                    if (actualState != Colonie.State.COLON) {
                        throw new Exception("Ligne "+ numeroLigne+" hors ordre attendu " );
                    }
                    if (split.length != 2) {
                        throw new Exception("Nombre d'arguments invalide pour le colon a la ligne " + numeroLigne);
                    } else if (!nomColon.contains(split[1])) {
                        nomColon.add(split[1]);
                        nbColon++;
                    }

                } else if (ligne.startsWith("ressource(")) {
                    if (actualState == Colonie.State.COLON) {
                        actualState = Colonie.State.RESSOURCE;
                    }
                    if (actualState != Colonie.State.RESSOURCE) {
                    	 throw new Exception("Ligne "+ numeroLigne+" hors ordre attendu " );
                    }
                    if (split.length != 2) {
                        throw new Exception("Nombre d'arguments invalide pour la ressource a la ligne " + numeroLigne);
                    } else if (!nomRessources.contains(split[1])) {
                        nomRessources.add(split[1]);
                        nbRessource++;
                    }

                } else if (ligne.startsWith("deteste(")) {
                    if (actualState == Colonie.State.RESSOURCE) {
                        actualState = Colonie.State.DETESTE;
                    }
                    if (actualState != Colonie.State.DETESTE) {
                    	 throw new Exception("Ligne "+ numeroLigne+" hors ordre attendu " );
                    }
                    if (split.length != 3) {
                        throw new Exception("Nombre d'arguments invalide pour deteste à la ligne " + numeroLigne);
                    } else if (!nomColon.contains(split[1]) || !nomColon.contains(split[2])) {
                        throw new Exception("Colons non existants à la ligne " + numeroLigne);
                    }

                } else if (ligne.startsWith("preferences(")) {
                    if (actualState == Colonie.State.DETESTE||actualState == Colonie.State.RESSOURCE) {
                        actualState = Colonie.State.PREFERENCES;
                    }
                    if (actualState != Colonie.State.PREFERENCES) {
                    	 throw new Exception("Ligne "+ numeroLigne +" hors ordre attendu " );
                    }
                    if (split.length != nbRessource + 2) {
                        throw new Exception("Nombre d'arguments invalide pour preferences à la ligne " + numeroLigne);
                    }
                    if (!nomColon.contains(split[1])) {
                        throw new Exception("Colon non existant à la ligne " + numeroLigne);
                    }
                    for (int i = 2; i < split.length; i++) {
                        if (!nomRessources.contains(split[i])) {
                            throw new Exception("Ressource non existante à la ligne " + numeroLigne);
                        }
                        if (Collections.frequency(List.of(split), split[i]) != 1) {
                            throw new Exception("Ressource présente plusieurs fois à la ligne " + numeroLigne);
                        }
                    }
                    nbPreferences++;
                }
                else {
                	throw new Exception("erreur a la ligne "+numeroLigne+ " chaque ligne doit commencer par colon/ressource/deteste/preferences");
                }
            }

            if (nbColon != nbPreferences) {
                throw new Exception("Préférences pas attribuées à tous les colons");
            }
            
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
}

