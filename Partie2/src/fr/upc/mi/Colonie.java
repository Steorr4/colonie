package fr.upc.mi;

import java.io.*;
import java.util.*;

/**
 * Simule le partage de ressources dans une colonie en fonction des relations
 * des colons et de leurs affinites.
 */
public class Colonie  {
    private List<Crewmate> crewmateList; //List car on ne connait pas le nombre de colons intial et la manipulation des List (ajout
    private List<Ressource> ressourceList;
    private AffectationStrategy affectationStrategy;
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
     * Methode d'effectation naive qui affecte a chaqu'un des colons de
     * crewmateList dans l'ordre la ressource qu'il prefere si disponible sinon la prochaine.
     *
     */

     public void affectationAuto() throws Exception{
    	 for (Crewmate cr : crewmateList) {
    		 cr.setrAssigned(null);
    	 }
    	 for(Ressource r:ressourceList) {
    		 r.setAvailable(true);
    	 }
        for (Crewmate c : crewmateList) {
            for(Ressource r : c.getPreferences()){
                if(r.isAvailable()) {
                    c.setrAssigned(r);
                    r.setAvailable(false);
                    break;
                }
            }
        }
        setEnviousList();
    }
    
     /**
      * Methode d'effectation naive qui affecte à chaqu'un des colons de
      * crewmateList dans l'ordre la ressource qu'il préfère si disponible sinon la prochaine.
      * Utile pour la methode algoAffectationOptimisee
      * 
      * @param crewmateList une liste de crewmates
      *
      */
    public void algoAffectation(List<Crewmate>crewmateList) {
    	 for (Crewmate cr : crewmateList) {
    		 cr.setrAssigned(null);
    	 }
    	 for(Ressource r:ressourceList) {
    		 r.setAvailable(true);
    	 }
        int nbJaloux = 0;
        for (Crewmate c : crewmateList) {
        	
            //int nbJalouxApresAffectation = nbJaloux;
            
            for (Ressource r : c.getPreferences()) {
            	
                if (r.isAvailable()) {
                    c.setrAssigned(r);
                    r.setAvailable(false);
                    setEnviousList();
          
                        break;
                    
                }
            }
            
            nbJaloux = nbEnvious();
            
        }
       
    }
   // / Méthode pour définir la stratégie à utiliser
    public void setAffectationStrategy(AffectationStrategy affectationStrategy) {
        this.affectationStrategy = affectationStrategy;
    }

    // Méthode pour appliquer la stratégie d'affectation
    public void appliquerAffectation() throws Exception {
        this.affectationStrategy.affecterRessources(this);
    }
    
    /**
     * Algorithme d'affectation de complexité O(n2) efficace pour un tres grand nombre de colons (100) mais pas pour un nombre petit.
     *
     * @param maxIterations le nombre d'iterations maximales.
     */
    
    public void algoAffectationOptimise(int maxIterations) {
        // Étape 1 : Initialisation
    	// Map pour sauvegarder la meilleure solution (nom du colon -> nom de la ressource)
        HashMap<String, String> meilleureAffectation = new HashMap<>();
        Collections.sort(crewmateList); // Trier la liste initialement
        List<Crewmate> meilleureListe = new ArrayList<>(crewmateList); // Cloner la liste
        algoAffectation(meilleureListe); // Application de l'affectation sur la liste triée
        int meilleurNbJaloux = nbEnvious();
        sauvegarderAffectation(meilleureAffectation); // Sauvegarde initiale

        // Étape 2 : Itérations avec ordres aléatoires
        for (int i = 0; i < maxIterations; i++) {
            // Mélanger aléatoirement la liste des colons
            Collections.shuffle(crewmateList);

            // Appliquer l'algorithme d'affectation
            algoAffectation(crewmateList);
            int nbJalouxCourant = nbEnvious();
            
            System.out.println("Nb jaloux actuel : " + nbJalouxCourant);
            
            // Comparer et mettre à jour si nécessaire
            if (nbJalouxCourant < meilleurNbJaloux) {
                meilleurNbJaloux = nbJalouxCourant;
                meilleureListe = new ArrayList<>(crewmateList);
                sauvegarderAffectation(meilleureAffectation); // Sauvegarde initiale// Sauvegarder la solution
                
            }

            // Rétablir la meilleure affectation pour éviter les biais
            crewmateList = new ArrayList<>(meilleureListe);
            restaurerAffectation(meilleureAffectation);
            if(meilleurNbJaloux==0) break;
        }

        // Étape 3 : Retourner la meilleure solution trouvée
        
        crewmateList = new ArrayList<>(meilleureListe); // Restaurer la meilleure liste finale
        printAffectations();
        printEnviousCrewmates();
        System.out.println("Meilleure affectation trouvée avec " + meilleurNbJaloux + " jaloux.");
        
    }

    	
    /**
     * Algorithme du prof amélioré avec tri de la liste de crewmate
     */
    public void algoAmeliore() throws Exception {
    	Collections.sort(crewmateList);//trie la liste selon le "degre" d'ennemies afin de reduire dès le depart le nombre de jaloux
    	affectationAuto();
        HashMap<String, String> meilleureAffectation = new HashMap<>();
    	List<Crewmate> meilleureListe = new ArrayList<>(crewmateList);
    	int meilleurNbJaloux = nbEnvious();
    	sauvegarderAffectation(meilleureAffectation);
    	for (int i = 0; i < 1000; i++) {
    		Crewmate pirate=crewmateList.get(generateRandomNumber(crewmateList.size()));
    		Crewmate voisin;
    		do {
    			voisin=crewmateList.get(generateRandomNumber(crewmateList.size()));
    		}while(voisin==pirate);
    		swapRessources(pirate,voisin);
    		int nbJalouxCourant = nbEnvious();
    		System.out.println("nbJalouxCourant "+nbJalouxCourant);
    		if(nbJalouxCourant < meilleurNbJaloux) {
    			meilleurNbJaloux = nbJalouxCourant;
                meilleureListe = new ArrayList<>(crewmateList);
                sauvegarderAffectation(meilleureAffectation);
    		}
    		crewmateList = new ArrayList<>(meilleureListe);
            restaurerAffectation(meilleureAffectation);
            if(meilleurNbJaloux==0) break;
    		
    		
    	}
    	crewmateList = new ArrayList<>(meilleureListe); // Restaurer la meilleure liste finale
        printAffectations();
        printEnviousCrewmates();
        System.out.println("Meilleure affectation trouvée avec " + meilleurNbJaloux + " jaloux.");

    }
    
    /**
     * algorithme inspire de algoAmeliore 
     */
   /* public void algoAmeliore2() throws Exception {
    	
       Collections.sort(crewmateList);//trie la liste selon le "degre" d'ennemies afin de reduire des le depart le nombre de jaloux
        affectationAuto();
        //permet de sauvegarder la solution en cas de perturbation globale(shuffle)
        HashMap<String, String> meilleureAffectation = new HashMap<>();
        sauvegarderAffectation(meilleureAffectation);
        int meilleurNbJaloux = nbEnvious();
        int nbIterationsSansAmelioration=0;
        List<Crewmate> envieux;
        for (int iteration = 0; iteration < 10000; iteration++) {
            envieux = crewmateList.stream().filter(Crewmate::isEnvious).collect(Collectors.toList());
            if (envieux.isEmpty()) break;

            //Crewmate pirate = envieux.get(generateRandomNumber(envieux.size()));
            Crewmate pirate=crewmateList.get(generateRandomNumber(crewmateList.size()));
            Crewmate voisin;
            do {
                voisin = crewmateList.get(generateRandomNumber(crewmateList.size()));
            } while (voisin == pirate);
 
            swapRessources(pirate, voisin);

            int nbJalouxCourant = nbEnvious();
            if (nbJalouxCourant < meilleurNbJaloux) {
                meilleurNbJaloux = nbJalouxCourant;
                nbIterationsSansAmelioration = 0; 
                sauvegarderAffectation(meilleureAffectation);
          
            } 
            //si aucune amelioration au bout de 100 on remelange la liste
            else if(nbIterationsSansAmelioration>150) {
            	System.out.println("shuffle");
            	Collections.shuffle(crewmateList);
            	affectationAuto();
            	nbIterationsSansAmelioration = 0;
           }
            else {
            	nbIterationsSansAmelioration++;
            	swapRessources(pirate, voisin);// Annule le swap si non bénéfique
            }
            	
               restaurerAffectation(meilleureAffectation); 

            if (meilleurNbJaloux == 0) break;
        }

        restaurerAffectation(meilleureAffectation);
        System.out.println("Meilleure affectation trouvée avec " + meilleurNbJaloux + " jaloux.");
        printAffectations();
        printEnviousCrewmates();
    }*/
    


    /**
     * Genere un nombre random entre 0 et n  exclu.
     * 
     * @param n la borne superieure exclu.
     */

    public static int generateRandomNumber(int n)
    {
        if (n < 0) {
            throw new IllegalArgumentException("n must not be negative");
        }

        return new Random().nextInt(n);
    }
    
    /**
     * Sauvegarde la solution actuelle
     * 
     * @param solution une hashmap qui contient le nom du colon et la ressource qu'on lui a affecte dans la solution.
     */
    public void sauvegarderAffectation(HashMap<String,String> solution) {
    	solution.clear();
        for (Crewmate c : crewmateList) {
            solution.put(c.getName(), c.getrAssigned().getName());
        }
    }
    
    
    /**
     * Restaure la liste crewmateList avec la solution.
     * 
     * @param solution une hashmap qui contient le nom du colon et la ressource qu'on lui a affecte dans la solution.
     */
    public void restaurerAffectation(HashMap<String,String> solution) {
    	for(Crewmate crewmate:crewmateList) {
    		Ressource r= Colonie.getRessource(solution.get(crewmate.getName()),ressourceList);
    		crewmate.setrAssigned(r);
    	}
    	setEnviousList();
    }
   
    /**
     * Affiche les affectations des ressources dans la colonie.
     */
    public void printAffectations() {
        for (Crewmate c : crewmateList) {
            System.out.println(c.getName() + " -> " +
                    (c.getrAssigned() != null ? c.getrAssigned().getName() : "Aucune ressource"));
        }
    }


    /**
     * Met à jour les etats des colons s'ils sont jaloux ou pas
     */
    
    public void setEnviousList(){ 
        for(Crewmate c : crewmateList){
            c.setEnvious(false);
        }
        for(Crewmate c : crewmateList){
            for(Ressource r : c.getPreferences()) {
                if(r.equals(c.getrAssigned())){
                    break;
                }
                for (Crewmate mechant : c.getRelations()) {
                    if (mechant.getrAssigned()==r){
                        c.setEnvious(true);
                        break;
                    }
                }
            }
        }
    }
    
    /**
     * Affiche les colons jaloux.
     */
    public void printEnviousCrewmates() {
        for (Crewmate c : crewmateList) {
            if (c.isEnvious()) {
                System.out.println(c.getName() + " est jaloux.");
            }
        }
    }
    /**
     * Permet un enchange direct des ressources entre deux colons.
     *
     * @param c1 le premier colon
     * @param c2 le deuxieme colon
     */
    public void swapRessources(Crewmate c1, Crewmate c2){
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
    public int nbEnvious(){
        int cpt = 0;
        for(Crewmate c : crewmateList){
            if(c.isEnvious()) cpt++;
        }
        return cpt;
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
    public List<Ressource> getRessourceList() {
		
		return ressourceList;
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
     
     /**
      * Enumeration des etats pour la verif de fichier.
      */

     public enum State{
         COLON,
         RESSOURCE,
         DETESTE,
         PREFERENCES;
     }
     
     
     /**
      * Verifie le fichier entré en second argument par l'utilisateur.
      * @param path le chemin du fichier
      * @return true si le fichier de format valide 
      * @throws IOException
      * @throws Exception
      */
     
     private boolean verifSauvegarde(String path)throws IOException, Exception{
    	 boolean verif=false;
    	 try {
 			BufferedReader br = new BufferedReader(new FileReader(path));
 			String ligne;
 			int numeroLigne=0;
 			while((ligne=br.readLine())!=null) {
 				numeroLigne++;
 				if(ligne.split(":").length!=2) {
 					throw new Exception("le format de la ligne "+numeroLigne+" est invalide");
 				}
 			
 				if(Colonie.getCrewmate(ligne.split(":")[0],crewmateList)==null) {
 					throw new Exception("l'individu de la ligne "+numeroLigne+" ne fait pas partie de la colonie");
 				}
 				if(Colonie.getRessource(ligne.split(":")[1],ressourceList)==null) {
 					throw new Exception("la ressource de la ligne "+numeroLigne+" ne fait pas partie des ressources de la colonie");
 				}	
 			}
 			if(numeroLigne!=crewmateList.size()) {
 				throw new Exception("Il manque les affectations de "+(crewmateList.size()-numeroLigne)+ "colons");
 			}
 			verif=true;
 			
 			
 			
 		} catch (FileNotFoundException e) {
 			
 			System.err.println("Fichier introuvable "+e.getMessage());
 		}
		return verif;
    	 
     }
     
     
     /**
      * Affectation des ressources selon le fichier sauvegarde de l'utilisateur
      * @param path le chemin du fichier de sauvegarde
      * @throws IOException
      * @throws Exception
      */
     public void save(BufferedWriter bw) {
    	 try {

  			 for(Crewmate c : crewmateList){
                   bw.write(c.getName()+":"+c.getrAssigned().getName());
                   bw.newLine();
             }

  		} catch (IOException e) {
  			System.err.println(e.getMessage());
  		}
     }
     
     /**
      * Recupere la ressource qui a le meme nom que nom
      * @param nom nom de la ressource rechecrhchée 
      * @param ressourceList la ressource si presente sinon null
      * @return la ressource si presente sinon null
      */
     public static Ressource getRessource(String nom, List<Ressource> ressourceList){
         for(Ressource r:ressourceList){
             if(r.getName().equals(nom)) return r;
         }
     return null;
     }
     
     
     
     /**
      * Recupere le colon qui a le meme nom que nom
      * @param nom nom du colon rechecrhche. 
      * @param crewmateList la ressource si presente sinon null
      * @return la colon si presente sinon null
      */
     public static Crewmate getCrewmate(String nom, List<Crewmate> crewmateList){
         for(Crewmate c:crewmateList){
             if(c.getName().equals(nom)) return c;
         }
     return null;
     }
     
     
     
     //////MENU PARTIE 1
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
        String line;
        String colon1_name;
        String colon2_name;
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


	
}









