package fr.upc.mi.paa.ui;

import fr.upc.mi.paa.affectation.AffectationAmelioree;
import fr.upc.mi.paa.affectation.AffectationBruteForce;
import fr.upc.mi.paa.affectation.AffectationLineaire;
import fr.upc.mi.paa.colonie.Colonie;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe permettant d'effectuer certaines interactions avec l'utilisateur.
 */
public class MenuPrincipal {

    private Scanner sc;
    private Colonie colonie;

    public MenuPrincipal(Scanner sc, Colonie colonie){
        this.sc = sc;
        this.colonie = colonie;
    }

    /**
     * Methode pour afficher le menu principal et interagir avec l'utilisateur pour qu'il applique les differents algos de
     * repartition à sa colonie ou bien enregistrer ses resultats.
     */
    public void getMenu(){
        long startTime = System.currentTimeMillis();
        boolean exit = false;
        int choix;

        BufferedWriter bw = null;

        try {
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

                        // Afficher un sous-menu pour choisir l'algorithme
                        int algoChoix;
                        do {
                            afficherMenuChoixAlgorithme();
                            try {
                                System.out.print("Votre choix d'algorithme : ");
                                algoChoix = sc.nextInt();
                                sc.nextLine(); // Consomme le retour à la ligne

                                if (algoChoix < 1 || algoChoix > 3) {
                                    System.err.println("Erreur : Veuillez choisir une option valide (1, 2 ou 3).");
                                }
                            } catch (InputMismatchException e) {
                                System.err.println("Erreur : Veuillez entrer un nombre entier valide.");
                                sc.nextLine(); // Nettoyer l'entrée
                                algoChoix = -1; // Réinitialiser le choix
                            }
                        } while (algoChoix < 1 || algoChoix > 3);

                        // Appliquer l'algorithme choisi
                        switch (algoChoix) {
                            case 1:
                                System.out.println("Application de l'algorithme d'affectation linéaire...");
                                colonie.setAffectationStrategy(new AffectationLineaire());
                                break;

                            case 2:
                                System.out.println("Application de l'algorithme Brute Force...");
                                colonie.setAffectationStrategy(new AffectationBruteForce());
                                break;

                            case 3:
                                System.out.println("Application de l'algorithme amélioré...");
                                colonie.setAffectationStrategy(new AffectationAmelioree());
                                break;
                        }

                        // Appliquer la stratégie
                        colonie.appliquerAffectation();

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
                        File file = new File(path);
                        bw = new BufferedWriter(new FileWriter(file));
                        colonie.save(bw);
                        System.out.println("Solution sauvegardée dans le fichier : " + path);
                        break;

                    case 0:
                        System.out.println("Programme terminé. À bientôt !");
                        exit = true;
                        break;
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
     * Afficher le menu des options.
     */
    private static void afficherMenu() {
        System.out.println("********************");
        System.out.println("Que souhaitez-vous faire ?");
        System.out.println("1. Appliquer la résolution automatique");
        System.out.println("2. Sauvegarder la solution actuelle");
        System.out.println("0. Quitter la colonie.");
        System.out.println("********************");
    }

    /**
     * Afficher le menu des choix d'algo.
     */
    private static void afficherMenuChoixAlgorithme() {
    	System.out.println("********************");
        System.out.println("Que souhaitez-vous faire ?");
        System.out.println("1. Appliquer l'algorithme d'affectation lineaire");
        System.out.println("2. Appliquer l'algorithme Brute Force");
        System.out.println("3. Appliquer l'algorithme ameliore de l'algorithme naif");
        System.out.println("********************");
    }

}
