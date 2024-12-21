package fr.upc.mi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Test manuel du programme
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = null;
        BufferedWriter bw = null;
        long startTime = System.currentTimeMillis();
        try {
            // Vérifier si un fichier est fourni en argument
            if (args.length < 1) {
                System.err.println("Erreur : Veuillez fournir le chemin du fichier en argument.");
                return;
            }

            String filePath = args[0];
            System.out.println("Fichier fourni : " + filePath);

            // Vérifier le fichier et configurer la colonie
            boolean verif = ColonieSetup.verifFichier(filePath);
            System.out.println("Fichier valide : " + verif);

            if (verif) {
                ColonieSetup colonieSetup = new ColonieSetup();
                Colonie colonie = colonieSetup.setUp(filePath);
                colonie.setAffectationStrategy(new AffectationLineaire());
                colonie.appliquerAffectation();

                boolean exit = false;
                int choix;
                sc = new Scanner(System.in);

                while (!exit) {
                    do {
                        afficherMenu();
                        try {
                            System.out.print("Votre choix : ");
                            choix = sc.nextInt();
                            sc.nextLine(); // Consomme le retour à la ligne

                            if (choix < 0 || choix > 2) {
                                System.err.println("Erreur : Veuillez choisir une option valide (0, 1 ou 2).");
                            }
                        } catch (InputMismatchException e) {
                            System.err.println("Erreur : Veuillez entrer un nombre entier valide.");
                            sc.nextLine(); // Nettoyer l'entrée
                            choix = -1; // Réinitialiser le choix
                        }
                    } while (choix < 0 || choix > 2);
 
                    // Exécution des options
                    switch (choix) {
                        case 1:
                            System.out.println("Exécution de la résolution automatique...");
                            //colonie.affectationAuto();
                            //// Changer la stratégie
                            colonie.setAffectationStrategy(new AffectationAmelioree());

                         // Appliquer la stratégie
                            colonie.appliquerAffectation();
                            //colonie.algoAmeliore2();
                            System.out.println("Nombre de jaloux après résolution : " + colonie.nbEnvious());
                            long endTime = System.currentTimeMillis();

                            // Calculer la durée d'exécution
                            long duration = endTime - startTime;

                            // Afficher la durée d'exécution
                            System.out.println("Durée d'exécution : " + duration + " millisecondes");
                            break;

                        case 2:
                            System.out.println("Entrez le nom du fichier de sauvegarde souaité : ");
                            String path = sc.nextLine();
                            bw = new BufferedWriter(new FileWriter(new File(path)));
                            colonie.save(bw);
                            System.out.println("Solution sauvegardée dans le fichier : " + path);
                            break;

                        case 0:
                            System.out.println("Programme terminé. À bientôt !");
                            exit = true;
                            break;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Une erreur inattendue est survenue : " + e.getMessage());
        } finally {
        	
            // Fermeture propre du scanner
            if (sc != null) {
                sc.close();
            }
            if (bw != null){
                try{
                    bw.close();
                } catch (IOException e) {
                    System.err.println("Erreur lors de la fermeture du BufferedWriter : " + e.getMessage());
                }
            }
        }
    }


    /**
     * Afficher le menu des options
     */
    private static void afficherMenu() {
        System.out.println("********************");
        System.out.println("Que souhaitez-vous faire ?");
        System.out.println("1. Appliquer la résolution automatique");
        System.out.println("2. Sauvegarder la solution actuelle");
        System.out.println("0. Quitter la colonie.");
        System.out.println("********************");
    }
}
