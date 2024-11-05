package v1;

import java.util.Vector;
public class Colon {
	private String id;
	private Vector<Colon> enemies;
	private Vector<Integer> preferences;
	private int ressource;
	
	public Colon(String id) {
		this.id=id;
		enemies=new Vector<Colon>();
		preferences=new Vector<Integer>();
		ressource=0;
	}
	public void setEnemies(Vector<Colon> enemies) {
		this.enemies=enemies;
	}
	
	public void setPreferences(Vector<Integer> preferences) {
		this.preferences=preferences;
	}
	public void setRessource(int ressource) {
		this.ressource=ressource;
	}
	
	public Vector<Colon> getEnemies(){
		return enemies;
	}
	
	public Vector<Integer> getPreferences(){
		return preferences;
	}
	
	public String getId() {
		return id;
	}
	
	
	public int getRessource() {
		return ressource;
	}
	
	
}
