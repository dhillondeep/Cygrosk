package game.states;

import game.core.GamePanel;

import java.awt.*;

// this class manages all the game states
public class GameStateManager
{
	// fields
	
	private int currentState;						// current state 
	protected final GamePanel gamePanel;			// gamePanel object
	
	private final int NUM_OF_STATES = 3;			// number of total states
	private final GameState [] states;				// array of game states
	
	// constants 
	public static final int MENU_STATE = 0;			// used to access menu state
	public static final int GAMEOVER_STATE = 1;		// used to access game over state
	public static final int M1L1_STATE = 2;			// used to access M1L1 state
	
	// constructor initializes fields
	public GameStateManager(GamePanel panel) 
	{
		// initialize states array
		states = new GameState[NUM_OF_STATES];
		
		// store game panel object
		this.gamePanel = panel;
		
		// set current state to menu state
		currentState = MENU_STATE;
		
		// load the current state
		loadState(currentState);
	} // end constructor
	
	// loadState method create a new state and make it current state
	private void loadState(int state)
	{
		// create a new object of the state and store it in the array
		
		if (state == MENU_STATE)
			states[MENU_STATE] = new MenuState(this);
		else if (state == M1L1_STATE)
			states[M1L1_STATE] = new M1Level1State(this);
		else if (state == GAMEOVER_STATE)
			states[GAMEOVER_STATE] = new GameOverState(this);	
	} // end loadState method
	
	// unloadState method unloads the state
	private void unloadState(int state)
	{
		// make the state null
		states[state] = null;
	} // end unloadState method
	
	// setState method sets the state
	public void setState(int state)
	{
		// unload the state
		unloadState(currentState);
		// set the current state
		currentState = state;
		// load that current state
		loadState(currentState);
	} // end setStae method
	
	// update method updates the game state
	public void update()
	{
		// call the update method from the current state
		if (states[currentState] != null) states[currentState].update();
	} // end update method
	
	// draw method draws the game state
	public void draw(Graphics g)
	{
		// call the draw method from the current state
		if (states[currentState] != null) states[currentState].draw(g);
	} // end draw method
} // end GameStateManager class