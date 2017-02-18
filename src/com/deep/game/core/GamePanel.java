package com.deep.game.core;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.deep.game.handlers.Keyboard;
import com.deep.game.handlers.Mouse;
import com.deep.game.states.GameStateManager;

// this class manages the game play
public class GamePanel extends JPanel implements Runnable
{
	// serial version id
	private static final long serialVersionUID = 2961762982572609626L;
	
	// declare all fields and global variables
	
	// size related
	private final int WIDTH;			// width of the GamePanel
	private final int HEIGHT;			// height of the GamePanel
	
	// thread related
	private Thread animationThread; 		// thread that is in charge of animation
	private final int FPS;					// FPS that game is running on
	private final long DESIRED_TIME;		// desired time per frame in a second
	private volatile boolean isRunning; 	// stops or starts the animation thread
	
	// drawing related
	private BufferedImage buffer;			// buffer to do drawing
	private Graphics bufferGraphics;		// Graphics object to manipulate or get graphics of the buffer
	private Graphics g;						// Graphics object to manipulate or get graphics of the GamePanel
	
	// game related
	private volatile boolean isGameOver; 				// boolean to check if the game is over or not
	private volatile boolean isPaused;					// boolean to check if the game is paused or not
	private static final int NO_SLEEP_MAX = 15;			// constant for maximum no sleeps
	private static final int FRAMES_SKIP_MAX = 20;		// constant for maximum frames skipped

	// game play related
	private Keyboard key;					// Keyboard object to listen to key inputs in the game
	private GameStateManager gsm;			// GameStateManager to control the flow of the game states

	private Mouse mouse;

	// constructor will initialize global variables and fields
	public GamePanel(int width, int height, int fps)
	{
		this.WIDTH = width;		// set width
		this.HEIGHT = height;	// set height
		this.FPS = fps;			// set FPS
		
		this.DESIRED_TIME = 1000000000L / this.FPS;	// calculate time per frame per second

		animationThread = null;	// set the animation Thread to null
		isRunning = false;		// set running to false since the animation thread is not executing

		// set the preferred size of this GamePanel
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	} // end Constructor

	// addNotify method will wait until game panel is added to the JFrame
	@Override
	public void addNotify() 
	{
		super.addNotify();	// call the super class version of this method
		startGame();		// start the game
	} // end addNotify method

	// startGame method initializes everything and starts the game
	private void startGame() 
	{
		// create the thread and initialize if it is not already running
		if (animationThread == null && !isRunning)
		{
			// create a new thread
			animationThread = new Thread(this);

			// create a keyboard object
			key = new Keyboard();
			
			// create a Mouse object
			mouse = new Mouse();
			
			// create a game state manager
			gsm = new GameStateManager(this);

			// add key listener
			addKeyListener(key);
			
			// add mouse listener
			addMouseListener(mouse);
			
			// add mouse motion listener
			addMouseMotionListener(mouse);

			// request focus
			requestFocus();
			
			// create the buffer if it is not already created
			if (buffer == null)
			{
				// create the buffer of the same size as the window
				buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
				
				// get the graphics from the buffer
				bufferGraphics = buffer.getGraphics();
			} // end if statement
			
			// start the animation thread
			animationThread.start();
		} // end if statement
	} // end startGame

	// stopGame method stops the animation thread
	public void stopGame()
	{
		// set isRunning boolean false
		isRunning = false;
	} // end stopGame

	// pauseGame method pauses the game
	public void pauseGame()
	{
		// set isPaused boolean true
		isPaused = true;
	} // end pauseGame

	// resumeGame method resumes the game
	public void resumeGame()
	{
		// set isPaused boolean false
		isPaused = false;
	} // end resumeGame

	// run method will contain the game loop and will update, render and sleep
	// this method will automatically be called when the animation thread will start
	@Override
	public void run() 
	{
		// variables to control FPS and UPS
		long startTime; 			// stores the start time. This is the time when loop repeats
		long currTime;  			// stores the current time. This is the current time at that point
		long timeElapsed;			// stores the time elapsed between an interval
		long sleepTime;				// stores the time for which the thread will sleep
		long overSleepTime;			// stores the time for which the thread over slept
		int noSleep;				// stores the number of time thread did not sleep
		long overUsedTime;			// stores the time that exceeds the desired time
		
		isRunning = true;	// make running true because the animation thread is running

		// get the start time when thread starts
		startTime = System.nanoTime();
		overSleepTime = 0;		// set overSleepTime to 0
		noSleep = 0;			// set noSleep to 0
		overUsedTime = 0;		// set overUsedTime to 0
		
		// run the game loop until isRunning boolean is true
		while(isRunning)
		{
			gameUpdate();	// update the game
			gameRender();	// render on to the buffer
			paintScreen();	// paint the buffer on the screen
			
			// find the current time
			currTime = System.nanoTime();
			// using the start time and current time, find timeElapsed
			timeElapsed = currTime - startTime;
			// based on time elapsed, decide sleep time. If program over slept, reduce the time this time
			sleepTime = (DESIRED_TIME - timeElapsed) - overSleepTime;
			
			// if update/render finishes before the desired time
			if (sleepTime >= 0)
			{
				try
				{
					// re store the current time 
					// This is done so that program precisely knows when the thread sleep is started
					currTime = System.nanoTime();
					
					// sleep the thread for the sleep time. Convert the sleep time to milliseconds
					Thread.sleep(sleepTime / 1000000L);
				}
				catch (InterruptedException e){}

				// find the overSleepTime if there is any
				// this done by checking if the time slept for the thread equals sleep time
				overSleepTime = (System.nanoTime() - currTime) - sleepTime;
			}
			// if update/render finishes after the desired time. This means it took more time
			else
			{
				// store excess time. This is done by adding the sleep time
				// since the sleep time is negative, convert the time to a positive number and then add
				overUsedTime = -1* sleepTime;

				// there is no over sleeping since we did not sleep this time
				overSleepTime = 0L;

				// add one to noSleep because this thread will be not sleep this time
				++noSleep;

				// if this thread did not sleep for allowed no sleep time
				if (noSleep >= NO_SLEEP_MAX)
				{
					// sleep the thread for 1 milliseconds so that other threads can get some time
					try 
					{
						Thread.sleep(1);
					} catch (InterruptedException e) {}
					
					// reset the noSleep since thread finally slept
					noSleep = 0;
				} // end if statement
			} // end if statement
			
			// variable to store how many frames are skipped in certain cases
			int skips = 0;

			// if there is enough over used time to complete one frame and maximums skips have not been reached,
			// only update the game and not render. This is done to save some time
			while ((overUsedTime > DESIRED_TIME) && (skips < FRAMES_SKIP_MAX))
			{
				// find the current time so that the program knows when game update started
				currTime = System.nanoTime();
				
				// update the game
				gameRender();
				
				// find the time it took for the game to update
				long elapsed = System.nanoTime() - currTime;
				
				// subtract that elapsed time from overUsedTime since that time is now used
				overUsedTime -= elapsed;
				
				// add one to the skips since one frame is skipped now
				skips++;
			} // end while loop
			
			// reset the start time
			startTime = System.nanoTime();
		} // end while loop
	} // end run method

	// gameRender method will render graphics on the buffer
	private void gameRender() 
	{
		// only render the game if it is not paused and it is not over
		if (!isPaused && !isGameOver)
		{
			// draw using game state manager
			gsm.draw(bufferGraphics);
		} // end if statement
	} // end gameRender method

	// gameUpdate method will update the game
	private void gameUpdate() 
	{
		// only update if the game is not paused and it is not over
		if (!isGameOver && !isPaused)
		{
			// update using game state manager
			gsm.update();
		} // end statement
	} // end gameUpdate method


	// paintScreen method paints the buffer on to the screen
	private void paintScreen() 
	{
		try
		{
			g = this.getGraphics();		// get the panel's graphics object

			// draw the buffer on screen if it and graphics object are not null
			if ((g != null) && (buffer != null))
				g.drawImage(buffer, 0, 0, null);
			
			// dispose the graphics object
			g.dispose();
		}
		catch (Exception e)
		{
			// print stack trace
			e.printStackTrace();
		}
	} // end paintScreen

	// getWidth method returns the width of the gamePanel
	public int getWidth()
	{
		// get the preferredSize and returns the width
		return getPreferredSize().width;
	} // end getWidth method

	// getHeight method returns the height of the gamePanel
	public int getHeight()
	{
		// get the preferredSize and returns the height
		return getPreferredSize().height;
	} // end getHeight method
} // end GamePanel class
