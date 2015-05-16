package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Deze klasse zal de display voor het aantal levens en de vuurballen die een persoon heeft,
 * verzorgen
 * 
 * @author Seppe Van Hees & Stijn Van Crombrugge
 */

public class HUD {
	
	private Player player;
	
	private BufferedImage image;
	private Font font;
	
	public HUD(Player p) {
		player = p;
		try {
			image = ImageIO.read(
				getClass().getResourceAsStream(
					"/HUD/hud.gif"
				)
			);
			font = new Font("Arial", Font.PLAIN, 14);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Deze methode tekent de display op het scherm
	 * 
	 * @param g de graphics voor het tekenen op het scherm
	 */
	
	public void draw(Graphics2D g) {
		
		g.drawImage(image, 0, 10, null);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(
			player.getHealth() + "/" + player.getMaxHealth(),
			30,
			25
		);
		g.drawString(
			player.getFire() / 100 + "/" + player.getMaxFire() / 100,
			30,
			45
		);
		
	}
	
}













