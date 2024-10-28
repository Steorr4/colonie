package fr.upc.mi.colonie;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Colonie {
    private List<Crewmate> crewmateList;
    private List<Ressource> ressourceList;

    public Colonie(List<Crewmate> crewmateList, List<Ressource> ressourceList) {
        this.crewmateList = crewmateList;
        this.ressourceList = ressourceList;
    }


    public static class ColonieSetup{
        public static Colonie setUp() throws IndexOutOfBoundsException{

            Scanner sc = new Scanner(System.in);
            int n = 0;
            System.out.println("Combien voulez vous de colons ? (1 - 26)");
            n = sc.nextInt();
            if (n<1 || n>26) throw new IndexOutOfBoundsException();
            sc.close();

            List<Crewmate> crewmates = new ArrayList<>();
            List<Ressource> ressources = new ArrayList<>();
            for(int i = 0; i<n; i++){
                crewmates.add(new Crewmate(String.valueOf((char)('A'+i))));
                ressources.add(new Ressource(i));
            }

            Colonie c = new Colonie(crewmates, ressources);
            menu();

            return c;
        }

        private static void menu(){
            //TODO
        }
    }
}
