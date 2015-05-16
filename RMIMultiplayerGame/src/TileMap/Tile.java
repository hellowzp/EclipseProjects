package TileMap;

import java.awt.image.BufferedImage;


/**
 * Deze klasse bepaalt één blok van de weergegeven map,
 * die dan een eigen type en eigen image heeft om weer te geven
 * Afhanklijk van de waarden normal of blocked is dit blokje doorzichtig
 * of niet
 * 
 * @param image de image behorend bij de ingegeven tile
 * @param type het type van de ingegeven tile
 * 
 * @author Seppe Van Hees & Stijn Van Crombrugge
 */

public class Tile {
	
	private BufferedImage image;
	private int type;
	
	// tile types
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	
	public Tile(BufferedImage image, int type) {
		this.image = image;
		this.type = type;
	}
	
	public BufferedImage getImage() { return image; }
	public int getType() { return type; }
	
}
