package fr.upc.mi.colonieV2;

import java.util.List;
import java.util.Scanner;

/**
 * Test manuel du programme
 */
public class Test {
    public static void main(String[]args){
        try {
        System.out.println("fichir  valide " + ColonieSetup.verifFichier("./fr/upc/mi/colonieV2/colonie.txt"));

        ColonieSetup colonieSetup= new ColonieSetup();
        Colonie colonie = colonieSetup.setUp("./fr/upc/mi/colonieV2/colonie.txt");
        System.out.println("colonie "+ colonie);
        System.out.println("nbJaloux "+ colonie.nbEnvious());









        }catch(Exception e){
            System.err.println("probleme" +e.getMessage());
        }
    }
}
