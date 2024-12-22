package fr.upc.mi.paa.colonie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ColonieSetup {

    public Colonie manualSetUp(Scanner sc) throws Exception {

        int n = 0;
        System.out.println("Combien voulez vous de colons ? (1 - 26)");
        n = sc.nextInt();
        if (n < 1 || n > 26) throw new IndexOutOfBoundsException();
        List<Crewmate> crewmates = new ArrayList<>();
        List<Ressource> ressources = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            crewmates.add(new Crewmate(String.valueOf((char) ('A' + i))));
            ressources.add(new Ressource("" + (i + 1)));
        }

        Colonie c = new Colonie(crewmates, ressources);
        manualMenu(c, n, sc);
        return c;
    }

    private void manualMenu(Colonie c, int n, Scanner sc) throws Exception {
        StringTokenizer st;
        int choix;
        boolean fin = false;
        String line;
        String colon1_name;
        String colon2_name;
        Crewmate c1 = null;
        Crewmate c2 = null;

        while (!fin) {
            do {
                System.out.println("********************");
                System.out.println("Que souhaitez vous faire ?");
                System.out.println("1. Ajouter une relation entre 2 colons.");
                System.out.println("2. Ajouter les preferences d'un colon.");
                System.out.println("0. Quitter ce menu.");
                System.out.println("********************");
                choix = sc.nextInt();
                sc.nextLine();
            } while (choix < 0 || choix > 2);

            switch (choix) {
                case 1:
                    System.out.println("Entrez les deux colons qui ne s'apprecient pas. (ex: 'A B')");
                    line = sc.nextLine();
                    st = new StringTokenizer(line, " ");
                    if (st.countTokens() != 2) throw new IndexOutOfBoundsException("!= 2");
                    colon1_name = st.nextToken();
                    colon2_name = st.nextToken();
                    for (Crewmate colon : c.getCrewmateList()) {
                        if (colon.getName().equals(colon1_name)) c1 = colon;
                        if (colon.getName().equals(colon2_name)) c2 = colon;
                    }
                    if (c1 == null || c2 == null) throw new Exception("Colon Innexistant");
                    setRelation(c1, c2);
                    break;

                case 2:
                    System.out.println("Entrez le nom du colon suivi de l'ordre de ses preferences (ex: A 1 3 2 ... n)");
                    line = sc.nextLine();
                    st = new StringTokenizer(line, " ");
                    colon1_name = st.nextToken();
                    for (Crewmate colon : c.getCrewmateList()) {
                        if (colon.getName().equals(colon1_name)) c1 = colon;
                    }
                    if (c1 == null) throw new Exception("Colon Innexistant");
                    c1.getPreferences().clear();
                    while (st.hasMoreTokens()) {
                        c1.getPreferences().add(c.getRessourceList().get(Integer.parseInt(st.nextToken()) - 1));
                    }
                    break;

                default:
                    fin = true;
            }
        }
    }

    private static void setRelation(Crewmate c1, Crewmate c2){
        c1.getRelations().add(c2);
        c2.getRelations().add(c1);
    }


	/**
	 * Initialise la colonie selon la configuration du fichier de l'utilisateur si aucune erreur ne survient.
	 * @param path
	 * @return la colonie correspondante a la configuration du fichier de l'utilisateur 
	 * @throws Exception
	 */

    public Colonie fileSetUp(String path) throws Exception {
            
                List<Crewmate> crewmateList = new ArrayList<>();
                List<Ressource> ressourceList = new ArrayList<>();
                BufferedReader br = new BufferedReader(new FileReader(path));
                boolean verifValide = verifFichier(path);
                String ligne;
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
                            		if(r.getName().equals(l[i])) {
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


    /**
     * Verifie si le format du fichier de l'utilisateur est valide
     * @param path le chemin du fichier de l'utilisateur
     * @return true si fichier valide false sinon
     */
    public static boolean verifFichier(String path) {
        try {
        	File file = new File(path);

            // Vérifiez si le fichier existe
            if (!file.exists()) {
                throw new FileNotFoundException("Le fichier spécifié est inexistant : " + path);
            }

            // Vérifiez si le fichier est vide
            if (file.length() == 0) {
                throw new IOException("Le fichier spécifié est vide : " + path);
            }
            
            List<String> nomColon = new ArrayList<>();
            List<String> nomRessources = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(path));
            String ligne;
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
                    nbColon=verifColon(split,numeroLigne,nbColon,actualState,nomColon);
                    
                } else if (ligne.startsWith("ressource(")) {
                    if (actualState == Colonie.State.COLON) {
                        actualState = Colonie.State.RESSOURCE;
                    }
                    nbRessource=verifRessource(split,numeroLigne,nbRessource,actualState,nomRessources);

                } else if (ligne.startsWith("deteste(")) {
                    if (actualState == Colonie.State.RESSOURCE) {
                        actualState = Colonie.State.DETESTE;
                    }
                    verifDeteste(split,numeroLigne,nbRessource,nbColon,actualState,nomColon,nomRessources);

                } else if (ligne.startsWith("preferences(")) {
                    if (actualState == Colonie.State.DETESTE||actualState == Colonie.State.RESSOURCE) {
                        actualState = Colonie.State.PREFERENCES;
                    }
                    
                    nbPreferences=verifPreferences(split,numeroLigne,nbRessource,nbColon,
                            nbPreferences,actualState,nomColon,nomRessources);
                }
                else {
                	throw new Exception("erreur a la ligne "+numeroLigne+
                            " chaque ligne doit commencer par colon/ressource/deteste/preferences");
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
    /**
     * Methode verifiant les lignes de colon
     * @param split
     * @param numeroLigne
     * @param nbColon
     * @param actualState
     * @param nomColon
     * @return
     * @throws Exception
     */
    
    public static int verifColon(String[] split, int numeroLigne, int nbColon,
                                 Colonie.State actualState, List<String> nomColon) throws Exception {
    	if (actualState != Colonie.State.COLON) {
            throw new Exception("Ligne "+ numeroLigne+" hors ordre attendu " );
        }
        if (split.length != 2) {
            throw new Exception("Nombre d'arguments invalide pour le colon a la ligne " + numeroLigne);
        } else if (!nomColon.contains(split[1])) {
            nomColon.add(split[1]);
            nbColon++;
        }
        return nbColon;
    	
    }
    /**
     * Methode verifiant les lignes de ressource
     * @param split
     * @param numeroLigne
     * @param nbRessource
     * @param actualState
     * @param nomRessources
     * @return
     * @throws Exception
     */
    public static int verifRessource(String[] split, int numeroLigne, int nbRessource,
                                     Colonie.State actualState, List<String> nomRessources) throws Exception {
    
        if (actualState != Colonie.State.RESSOURCE) {
        	 throw new Exception("Ligne "+ numeroLigne+" hors ordre attendu " );
        }
        if (split.length != 2) {
            throw new Exception("Nombre d'arguments invalide pour la ressource a la ligne " + numeroLigne);
        } else if (!nomRessources.contains(split[1])) {
            nomRessources.add(split[1]);
            nbRessource++;
        }
        return nbRessource;
    }
    
    /**
     * Methode verifiant les lignes de relation deteste
     * @param split
     * @param numeroLigne
     * @param nbRessource
     * @param nbColon
     * @param actualState
     * @param nomColon
     * @param nomRessources
     * @throws Exception
     */
    public static void verifDeteste(String[] split, int numeroLigne, int nbRessource, int nbColon,
                                    Colonie.State actualState, List<String> nomColon, List<String> nomRessources) throws Exception {
    	if (actualState != Colonie.State.DETESTE) {
       	 throw new Exception("Ligne "+ numeroLigne+" hors ordre attendu " );
       }
       if (split.length != 3) {
           throw new Exception("Nombre d'arguments invalide pour deteste à la ligne " + numeroLigne);
       } else if (!nomColon.contains(split[1]) || !nomColon.contains(split[2])) {
           throw new Exception("Colons non existants à la ligne " + numeroLigne);
       }

    }
    
    /**
     * Methode verifiant les lignes de preferences
     * @param split la ligne lue separée 
     * @param numeroLigne
     * @param nbRessource
     * @param nbColon
     * @param nbPreferences
     * @param actualState
     * @param nomColon
     * @param nomRessources
     * @return le nombre de preferences enregistrées
     * @throws Exception
     */
    public static int verifPreferences(String[] split, int numeroLigne, int nbRessource, int nbColon,
                                       int nbPreferences, Colonie.State actualState,
                                       List<String> nomColon, List<String> nomRessources) throws Exception{
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
       return nbPreferences;
    }

}


