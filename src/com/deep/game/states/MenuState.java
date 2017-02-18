package com.deep.game.states;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.deep.game.entity.PlayerAttributes;
import com.deep.game.handlers.Keyboard;
import com.deep.game.handlers.Mouse;
import com.deep.game.resources.Constants;
import com.deep.game.resources.Resources;

// this class deals with stuff when there is menu screen
public class MenuState extends GameState
{
	private BufferedImage [] menu_images;			// stores menu images
	private BufferedImage [] play_images;			// stores play images
	private BufferedImage instructions;				// stores instructions image
	private int currImage;							// variable to keep track of current image

	// constants
	private static final int NO_FOCUS = 0;			// when mouse arrow is not only any button
	private static final int PLAY_FOCUS = 1;		// when mouse arrow is on play button
	private static final int INST_FOCUS = 2;		// when mouse arrow is on instructions button
	private static final int QUIT_FOCUS = 3;		// when mouse arrow is on quit button

	private static final int MAIN_STATE = 0;		// if general menu screen is open
	private static final int PLAY_STATE = 1;		// if play menu screen is open
	private static final int INST_STATE = 2;		// if instructions screen is open

	private long changePlayerWait;					// maximum wait time between changing players in play menu
	private long changePlayerStartTime;				// start time when player is changed
	private boolean shouldChange;					// boolean to keep track if player should change

	private int currState;							// stores the current state

	private int currPlayer;							// stores the current player

	private boolean enterPressed;					// boolean to keep track if enter key is pressed

	// constructor initializes images
	public MenuState(GameStateManager gsm) 
	{
		super(gsm);

		// initialize everything
		initialize();
	} // end constructor

	@Override
	public void initialize()
	{
		// load images
		menu_images = Resources.getMenuImgs();				// get menu images from resources
		play_images = Resources.getPlayImgs();				// get play images from resources
		instructions = Resources.getInstructionsImg();		// get instructions image from resources

		currImage = NO_FOCUS;								// make the current image to be no focus image
		currState = MAIN_STATE;								// make current state to be main state

		shouldChange = true;								// allow user to change players
		changePlayerWait = 150;								// wait time between players is be 150 ms
	} // end initialize method

	// draw method draws the menu state
	@Override
	public void draw(Graphics g)
	{
		// draw the main screen if its is the current state
		if (currState == MAIN_STATE)
			// draw the current image
			g.drawImage(menu_images[currImage], 0, 0, null);
		// draw the play screen if its is the current state
		else if (currState == PLAY_STATE)
			g.drawImage(play_images[currPlayer], 0, 0, null);
		// draw the instructions screen if its is the current state
		else if (currState == INST_STATE)
			g.drawImage(instructions, 0, 0, null);
	} // end draw method

	// update method updates the menu state
	@Override
	public void update()
	{
		// get x coordinate for the mouse
		final int x = (int) Mouse.getMouseLocation().getX();
		// get y coordinate for the mouse
		final int y = (int) Mouse.getMouseLocation().getY();


		// switch statement based on current state
		switch (currState) 
		{
		// if main state
		case MAIN_STATE:

			// check if mouse is on play button
			if (x >= 478 && x <= 783 && y >= 243 && y <= 347)
			{
				// change the image to play focus
				currImage = PLAY_FOCUS;

				// if user clicks on play button
				if (Mouse.isLeftButtonPressed())
				{
					// sleep thread for 200 ms
					try 
					{
						Thread.sleep(200);
					} catch (InterruptedException e) 
					{
						e.printStackTrace();
					}

					// change the current state to play state
					currState = PLAY_STATE;
					// make the current player as player 1
					currPlayer = Constants.PLAYER1;
				} // end if statement
			}
			// check if mouse is on instructions button
			else if(x >= 478 && x <= 783 && y >= 383 && y <= 487)
			{
				// change the image to instructions focus
				currImage = INST_FOCUS;

				// if user clicks on instructions button
				if (Mouse.isLeftButtonPressed())
				{
					// sleep the thread for 200 ms
					try 
					{
						Thread.sleep(200);
					} catch (InterruptedException e) 
					{
						e.printStackTrace();
					}

					// change the current state to instructions state
					currState = INST_STATE;
				} // end if statement
			}
			// check if mouse is on quit button
			else if (x >= 1158 && x <= 1235 && y >= 18 && y <= 95)
			{
				// change the image to quit focus
				currImage = QUIT_FOCUS;

				// if user clicks on quit button, close the game
				if (Mouse.isLeftButtonPressed())
					System.exit(0);
			}
			// check if mouse is anywhere else on screen
			else
				// set the image to no focus
				currImage = NO_FOCUS;
			break;

			// if play state is selected
		case PLAY_STATE:
			inputHandler();

			// check if mouse is on quit button and user clicks it
			if (x >= 1158 && x <= 1235 && y >= 18 && y <= 95 && Mouse.isLeftButtonPressed())
				// quit the game
				System.exit(0);

			// if user presses enter key
			if (enterPressed)
			{
				// initialize the player attributes with current player
				PlayerAttributes.initialize(currPlayer);
				// set the state to M1L1 state 
				gsm.setState(GameStateManager.M1L1_STATE);
			} // end if statement
			break;

			// if instructions state is selected
		case INST_STATE:
			
			// check of mouse is on back button and user clicks on it
			if (x >= 22 && x <= 107 && y >= 22 && y <= 87 && Mouse.isLeftButtonPressed())
				// change the state to main state so that user can move back to main state
				currState = MAIN_STATE;
			break;
		} // end switch statement
	} // end update method

	// inputHandler method handles inputs
	@Override
	public void inputHandler()
	{		
		// get elapsed time until the play has been switched
		long elapsed = System.currentTimeMillis() - changePlayerStartTime;

		// if elapsed time is greater than change player wait time, make shouldChange boolean true
		if (elapsed >= changePlayerWait)
			shouldChange = true;

		// if shouldChange is true
		if (shouldChange)
		{
			// based on user keys, increment or decrease the value of currPlayer 
			if (Keyboard.isKeyPressed(Keyboard.RIGHT))
				++currPlayer;
			else if (Keyboard.isKeyPressed(Keyboard.LEFT))
				--currPlayer;

			// if currPlayer is less than 0 (first player), make it equal to 2 (last player)
			if (currPlayer < 0)
				currPlayer = 2;
			// if currPlayer is greater than 2 (last player), make it equal to 0 (first player)
			else if (currPlayer > 2)
				currPlayer = 0;	

			// reset the clock
			changePlayerStartTime = System.currentTimeMillis();
			
			// make should change false
			shouldChange = false;
		} // end if statement

		// based on if user has pressed enter key, make enterPressed boolean true or false
		if (Keyboard.isKeyPressed(Keyboard.ENTER))
			enterPressed = true;
		else 
			enterPressed = false;
	} // end inputHandler method
} // end MenuState class
