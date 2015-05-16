package GameState;

/**
 * Deze klasse verzorgt de verchillende states, één voor het menu,één voor de single player
 * modus en één voor de multiplayer modus
 * 
 * @author Seppe Van Hees & Stijn Van Crombrugge
 */

public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	
	public static final int NUMGAMESTATES = 3;
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
	public static final int MULTIPLAYER = 2;
	
	
	public GameStateManager() {
		
		gameStates = new GameState[NUMGAMESTATES];
		
		currentState = MENUSTATE;
		loadState(currentState);
		
	}
	
	
	/**
	 * Deze methode laadt één van de states als gamestate
	 * 
	 * @param state een int die de benodigde state vertegenwoordigt
	 */
	
	private void loadState(int state) {
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		if(state == LEVEL1STATE)
			gameStates[state] = new Level1State(this);
		if(state == MULTIPLAYER)
			gameStates[state] = new Multiplayer(this);
	}
	
	
	/**
	 * Deze methode zorgt voor het unladen van één van de states
	 * 
	 * @param state een int die de benodigde state vertegenwoordigt
	 */
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	
	/**
	 * Deze methode zet een state als huidige state door en laadt deze
	 * 
	 * @param state een int die de benodigde state vertegenwoordigt
	 */
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		
	}
	
	// updaten van het spel na een nieuwe state
	public void update() {
		try {
			gameStates[currentState].update();
		} catch(Exception e) {}
	}
	
	
	/**
	 * Deze methode hertekent het spel na een nieuwe state
	 * 
	 * @param g de benodigde graphics voor het hertekenen
	 */
	
	public void draw(java.awt.Graphics2D g) {
		try {
			gameStates[currentState].draw(g);
		} catch(Exception e) {}
	}
	
	
	/**
	 * Deze methode controleert op een ingedrukte knop en verzorgt op basis hiervan de gamestate
	 * 
	 * @param int van een bepaalde ingedrukte knop
	 */
	
	public void keyPressed(int k) {
		gameStates[currentState].keyPressed(k);
	}
	
	
	/**
	 * Deze methode controleert op het loslaten een ingedrukte knop en verzorgt op basis 
	 * hiervan de gamestate
	 * 
	 * @param int van een bepaalde knop die men losliet
	 */
	
	public void keyReleased(int k) {
		gameStates[currentState].keyReleased(k);
	}
	
}









