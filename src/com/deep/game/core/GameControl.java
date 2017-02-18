package com.deep.game.core;

import javax.swing.JFrame;

import com.deep.game.resources.Media;

// this class create and run the game
public class GameControl
{
	// fields and global variables
	
	private final int FPS;						// stores the FPS of the game
	private final long DESIRED_INTRO_TIME;		// desired time the introduction animation will run for
	private final int WIDTH;					// width of the window
	private final int HEIGHT;					// height of the window
	private JFrame frame;						// frame for game
	private GamePanel gamePanel;				// panel to handle game play
	private IntroPanel introPanel;				// panel to handle introduction animation
	private final String TITLE;					// title of the window

	// constructor creates a window
	public GameControl(String title) 
	{
		// set the title
		this.TITLE = title;

		// set the desired intro time to 7500 milliseconds
		this.DESIRED_INTRO_TIME = 7500;

		// set the global variables
		this.FPS = 60;

		// set the width and height
		this.WIDTH = 1260;
		this.HEIGHT = 700;

		// create JFrame
		create();

		// start the introPanel so that introduction for the game can be shown
		showIntro();

		// start the game
		startGame();
	}

	// create method creates all the components
	private void create() 
	{
		// create JFrame
		this.frame = new JFrame(TITLE);

		// create IntroPanel
		this.introPanel = new IntroPanel(WIDTH, HEIGHT);

		// create GamePanel
		this.gamePanel = new GamePanel(WIDTH, HEIGHT, FPS);
	}

	// showIntro method shows introduction animation on the screen
	private void showIntro() 
	{
		// create an instance of Media class and execute it as separate thread
		// this class will load game resources
		new Media().execute();
		
		// add the introPanel to the frame
		frame.add(introPanel);
	
		// get the current time and store it in a startTime variable
		long startTime = System.currentTimeMillis();
		
		// create the frame and then show it
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		// variable to store the elapsed time
		long elapsedTime;

		// wait for game introduction animation to finish
		do
		{
			// find the elapsed time since the introduction animation has started
			elapsedTime = System.currentTimeMillis() - startTime;
		}
		while (elapsedTime < DESIRED_INTRO_TIME);

		// remove the introPanel when introduction animation has finished
		frame.remove(introPanel);
	} // end  showIntro method

	// startGame method starts the game
	private void startGame() 
	{
		// add the gamePanel to the frame
		frame.add(gamePanel);

		// revalidate JFrame
		frame.revalidate();
		// repaint JFrame
		frame.repaint();
	} // end startGame method
} // end GameControl class