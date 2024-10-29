package fr.upc.mi.colonie;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Colonie {
    private List<Crewmate> crewmateList;
    private List<Ressource> ressourceList;

    public Colonie(List<Crewmate> crewmateList, List<Ressource> ressourceList) {
        this.crewmateList = crewmateList;
        this.ressourceList = ressourceList;
    }


    public static class ColonieSetup{
        public static Colonie setUp() throws Exception{

            Scanner sc = new Scanner(System.in);
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
                        sc.close();
                        fin = true;
                }
            }
        }

        private static void setRelation(Crewmate c1, Crewmate c2){
            c1.getRelations().add(c2);
            c2.getRelations().add(c1);
        }
    }

    //Getters & Setters (Colonie)
    public List<Crewmate> getCrewmateList() {
        return crewmateList;
    }

    public void setCrewmateList(List<Crewmate> crewmateList) {
        this.crewmateList = crewmateList;
    }

    public List<Ressource> getRessourceList() {
        return ressourceList;
    }

    public void setRessourceList(List<Ressource> ressourceList) {
        this.ressourceList = ressourceList;
    }
}
