package GameState;

import Main.GamePanel;
import TileMap.*;
import Entity.*;
import Entity.Enemies.*;
import Audio.AudioPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Deze klasse verzorgt de single player state van het spel
 * 
 * @author Seppe Van Hees & Stijn Van Crombrugge
 */

public class Level1State extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	
	private HUD hud;
	
	private AudioPlayer bgMusic;
	
	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	
	
	/**
	 * Deze methode initieert de map, achtergrond, player, explosies, HUD en audioplayer voor
	 * de single mode state
	 * 
	 */
	
	public void init() {
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/tileset.gif");
		tileMap.loadMap("/Maps/level1.1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/lvlbg1.gif", 0.1);
		
		player = new Player(tileMap);
		player.setPosition(100, 100);
		
		populateEnemies();
		
		explosions = new ArrayList<Explosion>();
		
		hud = new HUD(player);
		
		bgMusic = new AudioPlayer("/Music/level1-1.mp3");
		bgMusic.play();
		
	}
	
	
	// methode die de map bevolkt met enemies op verschillende plaatsen
	private void populateEnemies() {
		
		enemies = new ArrayList<Enemy>();
		
		Slak s;
		Point[] points = new Point[] {
			new Point(200, 100),
			new Point(860, 200),
			new Point(1525, 200),
			new Point(1680, 200),
			new Point(1800, 200),
			new Point(2300, 200),
			new Point(2700, 200),
			new Point(2800, 200),
			new Point(2900, 200)
		};
		for(int i = 0; i < points.length; i++) {
			s = new Slak(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}
		
	}
	
	// updaten van het spel
	public void update() {
		
		// update player
		player.update();
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);
		
		// check player dead
		if(player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
			player.reset();
			player.setPosition(100, 100);
			populateEnemies();
		}
		
		// set background
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		// attack enemies
		player.checkAttack(enemies);
		
		// update all enemies
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(
					new Explosion(e.getx(), e.gety()));
			}
		}
		
		// update explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
		
	}
	
	/**
	 * Deze methode verzorgt het hertekenen van de map na een bepaalde actie in single player
	 * modus
	 * 
	 * @param g de benodigde graphics voor het hertekenen
	 */
	
	public void draw(Graphics2D g) {
		
		bg.draw(g);
		tileMap.draw(g);
		player.draw(g);
		
		// draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		// draw explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition(
				(int)tileMap.getx(), (int)tileMap.gety());
			explosions.get(i).draw(g);
		}
		

		hud.draw(g);
		
	}
	
	
	
	/**
	 * Deze methode zorgt voor de nodige actie bij het indrukken van een
	 * bepaalde knop
	 * 
	 * @param k een int die de ingedrukete knop representeert
	 */
	
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_UP) player.setUp(true);
		if(k == KeyEvent.VK_DOWN) player.setDown(true);
		if(k == KeyEvent.VK_W) player.setJumping(true);
		if(k == KeyEvent.VK_E) player.setGliding(true);
		if(k == KeyEvent.VK_R) player.setKicking();
		if(k == KeyEvent.VK_F) player.setFiring();
		if(k == KeyEvent.VK_ESCAPE) {bgMusic.close(); gsm.setState(GameStateManager.MENUSTATE);}
	}
	
	
	/**
	 * Deze methode zorgt voor de nodige actie bij het loslaten van een
	 * bepaalde knop
	 * 
	 * @param k een int die de losgelaten knop representeert
	 */
	
	
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_UP) player.setUp(false);
		if(k == KeyEvent.VK_DOWN) player.setDown(false);
		if(k == KeyEvent.VK_W) player.setJumping(false);
		if(k == KeyEvent.VK_E) player.setGliding(false);
	}
	
}












