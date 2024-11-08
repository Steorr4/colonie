package fr.upc.mi.colonie;

import java.util.List;
import java.util.Scanner;

/**
 * Test manuel du programme
 */
public class Test {
    public static void main(String[]args){
        List<Crewmate> listRelation;
        List<Ressource> listPreferences;
        Scanner sc = new Scanner(System.in);

        try {
            Colonie c = Colonie.ColonieSetup.setUp(sc);
            for (Crewmate crewmate : c.getCrewmateList()){
                System.out.print(crewmate + ": ");
                listRelation = crewmate.getRelations();
                listPreferences = crewmate.getPreferences();
                if (listRelation != null) System.out.println(listRelation);
                if (listPreferences != null) System.out.println(listPreferences);
            }
            c.menu(sc);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sc.close();
        }

    }
}
