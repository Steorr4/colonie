package v1;

import java.util.Scanner;
import java.util.Vector;

/*
public class TestColonie {
	public static void main(String[] args) {
		
		System.out.println("Bonjour ! bienvenue à bord du vaisseau \n");
		System.out.println("Combien de colons voulez-vous ?");
		int n;
		Scanner sc=new Scanner(System.in);
		do {
			
			n=sc.nextInt();
			
		}while(n<0 || n>26);
		
		Colonie colonie=new Colonie(n);
		
		//test
		Vector<Integer> p1=new Vector<Integer>();
		p1.add(1);
		p1.add(2);
		Vector<Integer> p2=new Vector<Integer>();
		p2.add(1);
		p2.add(2);
		colonie.ajoutRelation("A", "B");
		colonie.preferences("A",p1 );
		colonie.preferences("B",p2 );
		colonie.affectationAuto();
		colonie.calculNbJaloux();
		System.out.println(colonie.getNbJaloux());
		
		
		
		
		
		
		
		
		
		
		
		do {
			System.out.println("1 : pour ajouter une relation entre deux colons \n");
			System.out.println("2 : pour ajouter les prefenrences d'un colon \n");
			System.out.println("3 : pour finir ! \n");
			int choix=sc.nextInt();
			switch(choix) {
				case 1: System.out.println("entrez les id des deux colons");
						colonie.ajoutRelation(sc.next(),sc.next());
						break;
				case 2: System.out.println("entrez l'id du colon et ses preferences");
						String id=sc.next();
						Vector<Integer> v= new Vector<Integer>();
						while(sc.hasNext()) {
							v.add(sc.nextInt());
						}
						colonie.preferences(id, v);
						break;
				case 3: System.out.println("fin");
						break;
			}
		}while(!colonie.verif());
		
	sc.close();
	
	
	}
}
*/