package fr.upc.mi.paa;

import fr.upc.mi.paa.colonie.*;
import fr.upc.mi.paa.affectation.*;
import fr.upc.mi.paa.ui.MenuPrincipal;

import java.util.Scanner;

/**
 * Test manuel du programme
 */
public class Application {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            // Vérifier si un fichier est fourni en argument
            if (args.length == 0) {

                ColonieSetup colonieSetup = new ColonieSetup();
                Colonie colonie = colonieSetup.manualSetUp(sc);
                colonie.setAffectationStrategy(new AffectationLineaire());
                colonie.appliquerAffectation();

                MenuPrincipal menu = new MenuPrincipal(sc, colonie);
                menu.getMenu();

            }else if(args.length == 1){

                String filePath = args[0];
                System.out.println("Fichier fourni : " + filePath);

                // Vérifier le fichier et configurer la colonie
                boolean verif = ColonieSetup.verifFichier(filePath);

                if (verif) {
                    ColonieSetup colonieSetup = new ColonieSetup();
                    Colonie colonie = colonieSetup.fileSetUp(filePath);
                    colonie.setAffectationStrategy(new AffectationLineaire());// au cas où l'utilisateur ne presse pas sur 1 on affecte lineairement
                    colonie.appliquerAffectation();

                    MenuPrincipal menu = new MenuPrincipal(sc, colonie);
                    menu.getMenu();
                }

            }else{
                System.err.println("Trop d'arguments fournis.");
            }

        } catch (Exception e) {
            System.err.println("Une erreur inattendue est survenue : " + e.getMessage());
        }
    }
}
