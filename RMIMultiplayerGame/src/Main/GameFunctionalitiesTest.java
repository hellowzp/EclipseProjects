package Main;
import static org.junit.Assert.*;
import org.junit.Test;
import Entity.Player;
import Entity.Enemies.Slak;
import TileMap.TileMap;

/**
 * 
 */

/**
 * 
 * Junit test klasse die enkele functionaliteit van de speler en enemy test.
 * Verdere testen werden niet geïmplementeerd, omdat deze reeds na het spelen
 * van het spel blijken te werken
 * 
 * @author Seppe Van Hees & Stijn Van Crombrugge
 *
 */
public class GameFunctionalitiesTest {
	
	
	@Test
	public void testPlayerFunctionalities() {
		Player player = new Player(new TileMap(5));
		player.hit(5);
		assertEquals("De betreffende speler heeft een getroffen aantal niet gedetecteerd",5,player.getHealth());
		player.reset();
		assertEquals("De betreffende speler werd niet hersteld na een reset",player.getMaxHealth(),player.getHealth());
		player.hit(player.getHealth());
		assertEquals("De betreffende speler werd niet gedood na een fatale aanval",true,player.isDead());
		
	}

	@Test
	public void testEnemySlakFunctionalities() {
		Slak slak = new Slak(new TileMap(5));
		slak.hit(3);
		assertEquals("De betreffende slak werd niet gedood na een fatale aanval",true,slak.isDead());
		Slak slak2 = new Slak(new TileMap(5));
		slak2.hit(1);
		assertEquals("De betreffende slak heeft een getroffen aantal niet gedetecteerd",1,slak2.getDamage());
		
	}
}
