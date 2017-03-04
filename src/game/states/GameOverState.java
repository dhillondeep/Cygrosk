package game.states;

import game.entity.PlayerAttributes;
import game.handlers.Mouse;
import game.resources.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;

// this class handles the things when game is over
public class GameOverState extends GameState
{
	// stores the game over image
	private BufferedImage gameOverImage;

	// constructor initialize the image
	public GameOverState(GameStateManager gsm) 
	{
		super(gsm);

		// initialize
		initialize();
	} // end constructor

	@Override
	public void initialize()
	{
		// get image from resources and store it 
		gameOverImage = Resources.getGameOverImg();
	}

	@Override
	public void draw(Graphics g)
	{
		// draw game over image
		g.drawImage(gameOverImage, 0, 0, null);

		// draw Strings on Screen
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 50));
		
		// show score
		g.drawString("Score: ", 330, 250);
		g.setColor(Color.MAGENTA);
		g.drawString(getString(PlayerAttributes.getScore()), 500, 252);

		// show coins 
		g.setColor(Color.BLACK);
		g.drawString("Coins: ", 330, 340);
		g.setColor(Color.MAGENTA);
		g.drawString(getString(PlayerAttributes.getCoins()), 500, 342);
	}

	// getString method returns String object when given an integer
	private String getString(int value) 
	{
		return "" + value;
	} // end getString method

	@Override
	public void update()
	{
		// get x value of the mouse
		int x = (int) Mouse.getMouseLocation().getX();
		// get y value of the mouse
		int y = (int) Mouse.getMouseLocation().getY();

		// if user clicks on the replay button
		if (x >= 233 & x <= 538 && y >= 440 && y <= 544 && Mouse.isLeftButtonPressed())
		{
			// reinitialize player attributes and pass in the same player number
			PlayerAttributes.initialize(PlayerAttributes.getPlayerNum());
			
			// set the state to M1L1 state
			gsm.setState(GameStateManager.M1L1_STATE);
		}
		// if user clicks on quit button, end the game 
		else if (x >= 723 && x <= 1027 && y >= 440 && y <= 544 && Mouse.isLeftButtonPressed())
			System.exit(0);
	} 

	@Override
	public void inputHandler()
	{
	}

}
