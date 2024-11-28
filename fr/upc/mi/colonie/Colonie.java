package fr.upc.mi.colonie;

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

    /**
     * Algorithme naif pour repartir les ressources enres les colons
     * en fonction des premiers en tete de liste.
     *
     * @throws Exception si la liste de preference d'un colon n'est pas set
     */
    private void affectationAuto() throws Exception{
        for (Crewmate c : crewmateList) {
            if(c.getPreferences().isEmpty()) throw new Exception("Liste de preference du colon "+
                    c.getName()+" vide");
            for(Ressource r : c.getPreferences()){
                if(r.isAvailable()) {
                    c.setrAssigned(r);
                    r.setProprietaire(c);
                    r.setAvailable(false);
                    break;
                }
            }
        }
        setEnviousList();
    }

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
        r1.setProprietaire(c2);
        c2.setrAssigned(r1);
        r2.setProprietaire(c1);
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
    public static class ColonieSetup{

        /**
         * Lance l'initialisation de la colonie.
         *
         * @param sc le scanner permattant de specifier nos choix
         * @return une colonie prealablement construite
         * @throws Exception si l'on specifie un nombre de colon hors de l'interval [1,26]
         */
        public static Colonie setUp(Scanner sc) throws Exception{

            int n = 0;
            System.out.println("Combien voulez vous de colons ? (1 - 26)");
            n = sc.nextInt();
            if (n<1 || n>26) throw new IndexOutOfBoundsException();

            List<Crewmate> crewmates = new ArrayList<>();
            List<Ressource> ressources = new ArrayList<>();
            for(int i = 0; i<n; i++){
                crewmates.add(new Crewmate(String.valueOf((char)('A'+i))));
                ressources.add(new Ressource(i+1));
            }

            Colonie c = new Colonie(crewmates, ressources);
            menu(c, n, sc);

            return c;
        }

        /**
         * Affiche un menu permettant de decider des relations et des preferences des colons
         * presents dans la colonie.
         *
         * @param c une colonie
         * @param n un entier representant le nombre de colon
         * @param sc le scanner permattant de specifier nos choix
         * @throws Exception si l'on demande une action sur un colon qui n'existe pas
         */
        private static void menu(Colonie c, int n, Scanner sc) throws Exception{
            StringTokenizer st;
            int choix;
            boolean fin = false;
            String line = null;
            String colon1_name = null;
            String colon2_name = null;
            Crewmate c1 = null;
            Crewmate c2 = null;


            while(!fin) {
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
                        st = new StringTokenizer(line," ");
                        if (st.countTokens() != 2) throw new IndexOutOfBoundsException("!= 2");
                        colon1_name = st.nextToken();
                        colon2_name = st.nextToken();
                        for (Crewmate colon : c.crewmateList){
                            if(colon.getName().equals(colon1_name)) c1 = colon;
                            if(colon.getName().equals(colon2_name)) c2 = colon;
                        }
                        if(c1 == null || c2 == null) throw new Exception("Colon Innexistant");
                        setRelation(c1,c2);
                        break;

                    case 2:
                        System.out.println("Entrez le nom du colon suivi de l'ordre de ses preferences (ex: A 1 3 2 ... n)");
                        line = sc.nextLine();
                        st = new StringTokenizer(line," ");
                        colon1_name = st.nextToken();
                        for(Crewmate colon : c.crewmateList){
                            if(colon.getName().equals(colon1_name)) c1 = colon;
                        }
                        if (c1 == null) throw new Exception("Colon Innexistant");
                        c1.getPreferences().clear();
                        while (st.hasMoreTokens()){
                            c1.getPreferences().add(c.ressourceList.get(Integer.parseInt(st.nextToken())-1));
                        }
                        break;

                    default:
                        fin = true;
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
