package GameState;

import Audio.AudioPlayer;
import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;


/**
 * Deze klasse verzorgt en onderhoudt de menustate van het spel
 * 
 * @author Seppe Van Hees & Stijn Van Crombrugge
 */


public class MenuState extends GameState {
	
	private Background bg;
	private int currentChoice = 0;
	private String[] options = {
		"Singleplayer",
		"Multiplayer",
		"Options",
		"Quit"
	};
	private Color titleColor;
	private Font titleFont;
	private Font font;
	private AudioPlayer menuMusic;
	
	
	public MenuState(GameStateManager gsm) {
		
		this.gsm = gsm;
		menuMusic = new AudioPlayer("/Music/LoginTheme.mp3");
		menuMusic.play();
		
		try {
			
			bg = new Background("/Backgrounds/menubg.gif", 1);
			bg.setVector(-0.1, 0);
			
			titleColor = new Color(128, 0, 0);
			titleFont = new Font(
					"Century Gothic",
					Font.PLAIN,
					28);
			
			font = new Font("Times New Roman", Font.PLAIN, 12);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void init() {}
	
	public void update() {
		bg.update();
	}
	
	
	/**
	 * Deze methode hertekent het spel na een bepaalde actie in de menustate
	 * 
	 * @param g de benodigde graphics voor het hertekenen
	 */
	
	
	public void draw(Graphics2D g) {
		
		bg.draw(g);
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Soft Dev Game", 70, 70);
		
		// draw menu options
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(Color.BLUE);
			}
			else {
				g.setColor(Color.RED);
			}
			g.drawString(options[i], 145, 140 + i * 15);
		}
		
	}
	
	

	// Het selecteren van een bepaalde optie voor volgende state in de menustate
	private void select() {
		if(currentChoice == 0) {
			menuMusic.close();
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		if(currentChoice == 1) {
			menuMusic.close();
			gsm.setState(GameStateManager.MULTIPLAYER);
		}
		if(currentChoice == 2) {
			
		}
		if(currentChoice == 3) {
			System.exit(0);
		}
	}
	
	
	/**
	 * Deze methode verzorgt de actie ne het indrukken van een knop
	 * 
	 * @param k een int die de ingedrukte knop vertegenwoordigt
	 */
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			if(currentChoice == 0){
				options[0] = "Loading";
			}
			if(currentChoice == 1){
				options[1] = "Loading";
			}
		}
		if(k == KeyEvent.VK_UP) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
		if(k == KeyEvent.VK_ESCAPE) System.exit(0);
	}
	
	
	/**
	 * Deze methode verzorgt de actie via de methode select() na het loslaten van een knop
	 * 
	 * @param k een int die de losgelaten knop vertegenwoordigt
	 */
	
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_ENTER){
			select();
		}
	}
}










