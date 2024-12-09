package fr.upc.mi.colonieV2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Simule le partage de ressources dans une colonie en fonction des relations
 * des colons et de leurs affinites.
 */
public class Colonie {
    private List<Crewmate> crewmateList;
    private List<Ressource> ressourceList;

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

    private void affectationAuto() throws Exception{
        //TODO
    }


    /**
     * TODO
     */
    private void setEnviousList(){
        for(Crewmate c : crewmateList){
            c.setEnvious(false);
        }
        for(Crewmate c : crewmateList){
            for(Ressource r : c.getPreferences()) {
                if(r.equals(c.getrAssigned())){
                    break;
                }
                for (Crewmate mechant : c.getRelations()) {
                    if (mechant.getrAssigned() == r){
                        c.setEnvious(true);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Permet un enchange direct des ressources entre deux colons.
     *
     * @param c1 le premier colon
     * @param c2 le deuxieme colon
     */
    private void swapRessources(Crewmate c1, Crewmate c2){
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
    private int nbEnvious(){
        int cpt = 0;
        for(Crewmate c : crewmateList){
            if(c.isEnvious()) cpt++;
        }
        return cpt;
    }

    /**
     * Le menu de la colonie permettant de decider si l'on veut echanger
     * les ressources entre des colons et afficher le nombre de colons
     * envieux au sein de la colonie.
     *
     * @param sc le scanner permettant de specifier nos choix
     * @throws Exception si l'on entre le nom d'un colon innexistant ou bien si l'on specifie
     * plus de 2 colons pour un simple echange
     */
    public void menu(Scanner sc) throws Exception{

        int choix;
        boolean exit = false;
        StringTokenizer st;
        String line = null;
        String colon1_name = null;
        String colon2_name = null;
        Crewmate c1 = null;
        Crewmate c2 = null;

        this.affectationAuto();

        while(!exit) {
            do {
                System.out.println("********************");
                System.out.println("Que souhaitez vous faire ?");
                System.out.println("1. Echanger les ressources entre 2 colons");
                System.out.println("2. Afficher le nombre de jaloux");
                System.out.println("0. Quitter la colonie.");
                System.out.println("********************");
                choix = sc.nextInt();
                sc.nextLine();
            } while(choix < 0 || choix > 2);

            switch(choix) {
                case 1:
                    System.out.println("Entrez les deux colons dont vous voulez echanger les ressources. (ex: 'A B')");
                        line = sc.nextLine();
                        st = new StringTokenizer(line," ");
                        if (st.countTokens() != 2) throw new IndexOutOfBoundsException("!= 2");
                        colon1_name = st.nextToken();
                        colon2_name = st.nextToken();
                        for (Crewmate colon : crewmateList){
                            if(colon.getName().equals(colon1_name)) c1 = colon;
                            if(colon.getName().equals(colon2_name)) c2 = colon;
                        }
                        if(c1 == null || c2 == null) throw new Exception("Colon Innexistant");
                        swapRessources(c1,c2);
                    break;
                case 2:
                    System.out.println(nbEnvious());
                    break;
                default:
                    exit = true;
            }
        }
    }

    /**
     * Permet d'initialiser une colonie.
     */
    public static class ColonieSetup {

        /**
         * Lance l'initialisation de la colonie.
         *///TODO
        public static Crewmate getCrewmate(String nom,List<Crewmate> crewmateList){
            for(Crewmate c:crewmateList){
                if(c.getName().equals(nom)) return c;
            }
        return null;
        }

        public static Colonie setUp(String path) throws Exception {
            try {
                List<Crewmate> crewmateList = new ArrayList<>();
                List<Ressource> ressourceList = new ArrayList<>();
                BufferedReader br = new BufferedReader(new FileReader(path));
                boolean verifValide = verifFichier(path);
                String ligne=null;
                String separateur = new String("(),.");

                if(verifValide){
                    while ((ligne = br.readLine()) != null) {
                        String[] l=ligne.split(separateur);
                        if (ligne.startsWith("colon")){
                            crewmateList.add(new Crewmate(l[1]));
                        }
                        if(ligne.startsWith("ressource")) {
                            for (int i = 1; i < crewmateList.size(); i++) {
                                ressourceList.add(new Ressource(l[i]));
                            }
                        }
                        if(ligne.startsWith("deteste")) {
                            Crewmate c1 = getCrewmate(l[1], crewmateList);
                            Crewmate c2 = getCrewmate(l[2], crewmateList);
                            c1.addRelation(c2);
                            c2.addRelation(c1);
                        }
                        if(ligne.startsWith("preferences")) {
                            Crewmate c = getCrewmate(l[1], crewmateList);

                            for (int i = 2; i < crewmateList.size(); i++) {
                                c.getPreferences().add(new Ressource(l[i]));
                            }
                        }

                    }
                    br.close();

                    return new Colonie(crewmateList, ressourceList);
                }
                
                else throw new Exception("fichier de format invalide");

            } catch (Exception e) {
                System.err.println("Erreur: "+e.getMessage());
            }
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


                    State state = State.COLON;
                    StringTokenizer st;

                    while ((ligne = br.readLine()) != null) {
                        State actualState = State.COLON;
                        String separateur = new String("(),.");
                        String[] split = ligne.split(separateur);


                        if (!ligne.endsWith(").")) {
                            throw new Exception("une ligne ne finit pas avec un point");
                        }
                        if (ligne.startsWith("colon(") && actualState == State.COLON) {

                            if (split.length != 2) {
                                throw new Exception("pas assez d'arguments");
                            } else if (!nomColon.contains(split[1])) {
                                nomColon.add(ligne.split(separateur)[1]);
                                nbColon++;
                            }
                            //TODO creer colon et ajouter a la liste

                        } else if (ligne.startsWith("ressource(")) {
                            if (State.RESSOURCE.compareTo(actualState) >= 0 && nbRessource <= nbColon) {
                                actualState = State.RESSOURCE;

                                if (split.length != 2) {
                                    throw new Exception("pas assez d'arguments");
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
                            if (State.DETESTE.compareTo(actualState) >= 0) {
                                actualState = State.DETESTE;
                                if (split.length != 3) {
                                    throw new Exception("pas assez d'arguments");
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
                            if (State.PREFERENCES.compareTo(actualState) >= 0 && nbPreferences <= nbColon) {
                                actualState = State.PREFERENCES;

                                if (split.length != nbColon + 2) {
                                    throw new Exception("pas assez d'arguments");
                                }

                                if (!nomColon.contains(split[1])) {
                                    throw new Exception("colons non existants");
                                }

                                for (int i = 2; i < split.length; i++) {
                                    if (!nomRessources.contains(split[i])) {
                                        throw new Exception("ressource non existantes");
                                    }
                                }

                                nbPreferences++;

                            } else {
                                throw new Exception("pas ordonné");
                            }
                        }
                    }
                    if (nbColon != nbPreferences) throw new Exception("preferences pas attribuées a tout les colons");
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                return true;
            }
        }

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

        private enum State{
            COLON,
            RESSOURCE,
            DETESTE,
            PREFERENCES;
        }
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

}
