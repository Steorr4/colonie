package v1;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class Colonie {
	private int n;
	private List<Colon> colons;
	private List<Integer> ressourcesDispo;
	private HashMap<Integer,Colon> affectations;
	private int nbJaloux;
	private static String[] ID= {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	public Colonie(int n) {
		this.n=n;
		ressourcesDispo=new Vector<Integer>(n);
		colons=new Vector<Colon>(n);
		for(int i=0;i<n;i++) {
			ressourcesDispo.add(i+1);
			colons.add(new Colon(ID[i]));
		}
		
		nbJaloux=0;
		affectations= new HashMap<Integer,Colon>(n);
	}
	
	public void ajoutRelation(String id1,String id2) {
		Colon c1=null;
		Colon c2=null;
		for(Colon c:colons) {
			if(c.getId().equals(id1)) {
				c1=c;
			}
			if(c.getId().equals(id2)) {
				c2=c;		
			}
		}
		if(c1==null || c2==null) {
			System.out.println("l'un/deux des colons de ces id n'est pas a bord");
		}
		if(!c1.getEnemies().contains(c2)) {
			c1.getEnemies().add(c2);
			c2.getEnemies().add(c1);
		}
	}
	
	
	public void preferences(String id,Vector<Integer> pref) {
		for(Colon c:colons) {
			if(c.getId().equals(id)) {
				c.setPreferences(pref);
			}
		}
	}
	
	
	public void affectationAuto() {
		//int jalousie=0;
		for(Colon c: colons) {
			boolean affecte = false;
			int i=0;
			while(!affecte && i<n ) {
				if(!ressourcesDispo.contains(c.getPreferences().get(i))){
					i++;
					//if(c.getEnemies().contains(affectations.get(((Vector<Integer>) c.getPreferences()).elementAt(i)))) {
					//	jalousie++;
					//}
				}
				else {
					affecte=true;
					c.setRessource( c.getPreferences().get(i));
					affectations.put(c.getPreferences().get(i),c);
					ressourcesDispo.remove( c.getPreferences().get(i));
				}
			}
		}
		//if(jalousie !=0) {
			//nbJaloux++;
		//}
	}
	
	public void echangerRessource(String id1,String id2) {
		Colon c1=null;
		Colon c2=null;
		for(Colon c:colons) {
			if(c.getId().equals(id1)) {
				c1=c;
			}
			if(c.getId().equals(id2)) {
				c2=c;		
			}
		}
		if(c1==null || c2==null) {
			System.out.println("l'un/deux des colons de ces id n'est pas a bord");
		}
		else {
			int i=c1.getRessource();
			c1.setRessource(c2.getRessource());
			c2.setRessource(i);
			affectations.put(c1.getRessource(),c1);
			affectations.put(c2.getRessource(),c2);
		}
		
	}
	
	public void calculNbJaloux() {
		for(Colon c:colons) {
			boolean j=false;
			int i=0;
			while((c.getPreferences()).get(i)!=c.getRessource() && !j ) {
				if(c.getEnemies().contains(affectations.get(c.getPreferences().get(i)))) {
					j=true;
				}
				i++;
			}
			if(j) {
				nbJaloux++;
			}
		}
	}
	
	
	public boolean verif() {
		boolean complet=true;
		for(Colon c: colons) {
			if(c.getPreferences().size()!=n) {
				System.out.println(" il faut remplir les preferences du colon "+ c.getId());
				complet=false;
			}
		}
		return complet;
	}
	public int getNbJaloux() {
		return nbJaloux;
	}
}
