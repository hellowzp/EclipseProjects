package Entity;

import java.awt.image.BufferedImage;

/**
 * Deze klasse zal animaties van de verschillende objecten van het spel visueel verzorgen
 * 
 * @author Seppe Van Hees & Stijn Van Crombrugge
 */

public class Animation {
	
	private BufferedImage[] frames;
	private int currentFrame;
	
	private long startTime;
	private long delay;
	
	private boolean playedOnce;
	
	public Animation() {
		playedOnce = false;
	}
	
	
	/**
	 * Deze methode zal de frames juist zetten bij het afspelen van een animatie
	 * 
	 * @author frames een BufferedImage van de frames die juist gezet moeten worden
	 */
	
	
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}
	
	public void setDelay(long d) { delay = d; }
	public void setFrame(int i) { currentFrame = i; }
	
	
	
	// Het updaten van de frames bij een animatie
	public void update() {
		
		if(delay == -1) return;
		
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if(elapsed > delay) {
			currentFrame++;
			startTime = System.nanoTime();
		}
		if(currentFrame == frames.length) {
			currentFrame = 0;
			playedOnce = true;
		}
		
	}
	
	public int getFrame() { return currentFrame; }
	public BufferedImage getImage() { return frames[currentFrame]; }
	public boolean hasPlayedOnce() { return playedOnce; }
	
}
















