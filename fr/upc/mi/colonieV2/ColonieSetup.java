package fr.upc.mi.colonieV2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class ColonieSetup {
    public Crewmate getCrewmate(String nom, List<Crewmate> crewmateList){
            for(Crewmate c:crewmateList){
                if(c.getName().equals(nom)) return c;
            }
        return null;
    }


    public Colonie setUp(String path) throws Exception {
            try {
                List<Crewmate> crewmateList = new ArrayList<>();
                List<Ressource> ressourceList = new ArrayList<>();
                BufferedReader br = new BufferedReader(new FileReader(path));
                boolean verifValide = verifFichier(path);
                String ligne=null;
                String separateur = "[(),.]";

                if(verifValide){
                    System.out.println("verif "+verifValide);
                    while ((ligne = br.readLine()) != null) {
                        String[] l = ligne.split(separateur);
                        if (ligne.startsWith("colon")) {
                            crewmateList.add(new Crewmate(l[1]));
                        }
                        if (ligne.startsWith("ressource")) {

                            ressourceList.add(new Ressource(l[1]));
                        }
                        if (ligne.startsWith("deteste")) {

                            Crewmate c1 = getCrewmate(l[1], crewmateList);
                            Crewmate c2 = getCrewmate(l[2], crewmateList);
                            c1.addRelation(c2);
                            c2.addRelation(c1);
                        }
                        if (ligne.startsWith("preferences")) {

                            Crewmate c = getCrewmate(l[1], crewmateList);
                            for (int i = 2; i < ressourceList.size(); i++) {
                                c.getPreferences().add(new Ressource(l[i]));
                            }
                        }
                    }


                    br.close();


                }

                else throw new Exception("fichier de format invalide");
                return new Colonie(crewmateList, ressourceList);
            } catch (Exception e) {
                System.err.println("Erreur: "+e.getMessage());
            }
            return null;

    }



    public static boolean verifFichier (String path){
                try {

                    List<String> nomColon = new ArrayList<>();
                    BufferedReader br = new BufferedReader(new FileReader(path));
                    String ligne = null;
                    int nbColon = 0;
                    int nbRessource = 0;
                    int nbPreferences = 0;
                    List<String> nomRessources = new ArrayList<>();


                    Colonie.State state = Colonie.State.COLON;
                    StringTokenizer st;

                    while ((ligne = br.readLine()) != null) {
                        Colonie.State actualState = Colonie.State.COLON;
                        String separateur = "[(),.]";
                        String[] split = ligne.split(separateur);


                        if (!ligne.endsWith(").")) {
                            throw new Exception("une ligne ne finit pas avec un point");

                        }
                        if (ligne.startsWith("colon(") && actualState == Colonie.State.COLON) {

                            if (split.length != 2) {
                                System.out.println("ligne :"+split[0]);
                                throw new Exception("pas assez d'arguments colon");
                            } else if (!nomColon.contains(split[1])) {
                                nomColon.add(ligne.split(separateur)[1]);
                                nbColon++;
                            }


                        } else if (ligne.startsWith("ressource(")) {
                            if (Colonie.State.RESSOURCE.compareTo(actualState) >= 0 && nbRessource <= nbColon) {
                                actualState = Colonie.State.RESSOURCE;

                                if (split.length != 2) {
                                    throw new Exception("pas assez d'arguments ressource");
                                } else if (!nomRessources.contains(split[1])) {
                                    nomRessources.add(ligne.split(separateur)[1]);
                                    nbRessource++;
                                }

                            } else {
                                throw new Exception("pas ordonné");
                            }

                        } else if (ligne.startsWith("deteste(")) {
                            if (nbColon != nbRessource) {
                                throw new Exception("nombre de colons differents du nombre de ressources");
                            }
                            if (Colonie.State.DETESTE.compareTo(actualState) >= 0) {
                                actualState = Colonie.State.DETESTE;
                                if (split.length != 3) {
                                    throw new Exception("pas assez d'arguments deteste");
                                } else if (!nomColon.contains(split[1]) || !nomColon.contains(split[1])) {
                                    throw new Exception("colons non existants");
                                }

                            } else {
                                throw new Exception("pas ordonné");
                            }

                        } else if (ligne.startsWith("preferences(")) {
                            if (nbColon != nbRessource) {
                                throw new Exception("nombre de colons differents du nombre de ressources");
                            }
                            if (Colonie.State.PREFERENCES.compareTo(actualState) >= 0 && nbPreferences <= nbColon) {
                                actualState = Colonie.State.PREFERENCES;

                                if (split.length != nbColon + 2) {
                                    throw new Exception("pas assez d'arguments preferences");
                                }

                                if (!nomColon.contains(split[1])) {
                                    throw new Exception("colons non existants");
                                }

                                for (int i = 2; i < split.length; i++) {

                                    if (!nomRessources.contains(split[i])) {
                                        throw new Exception("ressource non existantes");
                                    }
                                    if(Collections.frequency(List.of(split),split[i])!=1) {
                                        throw new Exception("ressource presente plusieurs fois");
                                    }

                                }

                                nbPreferences++;

                            } else {
                                throw new Exception("pas ordonné");
                            }
                        }
                    }
                    if (nbColon != nbPreferences) throw new Exception("preferences pas attribuées a tout les colons");
                    return true;
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                return false;
            }
        }

