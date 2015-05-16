package Entity;

import TileMap.TileMap;

/**
 * Deze klasse is een superklasse voor al de enemy objecten in het spel in single player modus
 * 
 * @author Seppe Van Hees & Stijn Van Crombrugge
 */

public class Enemy extends MapObject {
	
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;
	
	protected boolean flinching;
	protected long flinchTimer;
	
	public Enemy(TileMap tm) {
		super(tm);
	}
	
	public boolean isDead() { return dead; }
	
	public int getDamage() { return damage; }
	
	
	/**
	 * Deze methode controleert bij een hit van de enemy na een aanval wat de damage is en 
	 * past de health van deze enemy aan op basis van het soort aanval
	 * 
	 * @param damage een int van de damage op basis van het type aanval 
	 */
	
	public void hit(int damage) {
		if(dead || flinching) return;
		health -= damage;
		this.damage = damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	
	public void update() {}
	
}














