package GameState;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Audio.AudioPlayer;
import Entity.Explosion;
import Entity.HUD;
import Entity.Player;
import Main.GamePanel;
import RMI.Client;
import RMI.ServerInterface;
import TileMap.Background;
import TileMap.TileMap;


/**
 * Deze methode verzorgt de multiplayer mode van het spel indien een rmi werd opgestart
 * 
 * @param gsm de gamestatemanager
 * 
 * @author Seppe Van Hees & Stijn Van Crombrugge
 */


public class Multiplayer extends GameState {
	
	private static String host;
	private Client client; 
	private ServerInterface server; 
	private static TileMap tileMap;
	private Background bg;
	private static Player player;
	private static Player enemy;
	private ArrayList<Explosion> explosions;
	private HUD hud;
	
	public Multiplayer(GameStateManager gsm) {
		this.gsm = gsm;
        host = JOptionPane.showInputDialog("Enter the host of the chatserver", "localhost");   
		if (host != null ) {
			try {
				connect();
			} catch (Exception e) {
				e.printStackTrace();
				gsm.setState(GameStateManager.MENUSTATE);
			}
		}
		else gsm.setState(GameStateManager.MENUSTATE);	
	}
	

	// Het verzorgen van een connectie met de rmi
	public void connect() throws MalformedURLException, RemoteException, NotBoundException {
		server = (ServerInterface)Naming.lookup("//" + host + "/Server");
		client = new Client();
		server.login(client);
		init();
	}
	
	
	// Het initialiseren van de map en benodigde objecten in multiplayer mode
	public void init() {
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/tileset.gif");
		tileMap.loadMap("/Maps/arena.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/lvlbg1.gif", 0.1);
		
		player = new Player(tileMap);
		enemy = new Player(tileMap);
		player.setPosition(300, 100);
		
		explosions = new ArrayList<Explosion>();
		
		hud = new HUD(player);

		AudioPlayer bgMusic = new AudioPlayer("/Music/Ezreal.startup.mp3");
		bgMusic.play();
		
	}
	
	
	// Het opstarten van een nieuwe audioplayer
	public static void newPlayer(){
		AudioPlayer connect = new AudioPlayer("/SFX/FightSoundEffect.mp3");
		connect.play();
		
	}
	
	/**
	 * Deze methode update de speler op basis van zijn positie en health
	 * 
	 * @param x de xpositie van de speler
	 * @param y de ypositie van de speler
	 * @param hp de health van de speler
	 */
	
	public static void updatePlayer(int x , int y,int hp) {
		enemy.setPosition(x,y);
		if (player.getHealth() > hp){
			int dmg = player.getHealth() - hp;
			player.hit(dmg);
		}
	}
	
	/**
	 * Deze methode eindigt het spel op basis van een winner en toont dit op het scherm
	 * 
	 * @param winner een string van de winnaar van het spel
	 */
	
	public static void endGame(String winner){
		Component frame = null;
		JOptionPane.showMessageDialog(frame,winner);
		System.exit(0);
	}
	
	
	// updaten van het spel
	public void update() {
		
		player.update();
		enemy.update();
		
		try {
			server.updatePlayer(client,player.getx(),player.gety(),enemy.getHealth());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);
		
		// set background
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		// attack enemies
		player.checkDmg(enemy);
		
		// check dead
		if(player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
			try {
				server.playerWins(client);
			} catch (RemoteException e) {
				e.printStackTrace();
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
	 * Deze methode zorgt voor het drawen van het spel in multiplayer mode
	 * 
	 * @param g de benodigde graphics voor het hertekenen
	 */
	
	public void draw(Graphics2D g) {
		
		bg.draw(g);
		tileMap.draw(g);
		player.draw(g);
		enemy.draw(g);
		
		// draw explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition(
				(int)tileMap.getx(), (int)tileMap.gety());
			explosions.get(i).draw(g);
		}
		
		// draw hud
		hud.draw(g);
		
	}
	
	
	/**
	 * Deze methode zorgt voor de nodige actie bij het indrukken van een knop door de speler
	 * 
	 * @param k een int die de bepaalde knop vertegenwoordigt
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
		if(k == KeyEvent.VK_ESCAPE) System.exit(0);
	}
	
	
	/**
	 * Deze methode zorgt voor de nodige actie bij het loslaten van een ingedrukt knop
	 * 
	 * @param k een int die de bepaalde knop vertegenwoordigt
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
