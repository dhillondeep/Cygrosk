package game.tileMap;

import game.core.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

// this class is the base class for every background screen
public abstract class Background 
{
	// fields and global variables

	// background related
	protected BufferedImage image;			// stores the background image
	protected final int TYPE;				// stores the type of the background

	// movement related
	protected final GamePanel panel;		// store an object of GamePanel
	protected double moveSpeed;				// stores the move speed of the background
	protected double x;						// x position of the background
	protected double y;						// y position of the background

	// constants
	public static final int STILL = 0;		// constant for still background
	public static final int MOVE = 1;		// constant for moving background

	// this constructor should be used when background is still
	public Background(BufferedImage image, double x, double y)
	{
		// call the main constructor to initialize everything
		this(image, x, y, 0, null, STILL);
	} // end constructor

	// this constructor should be used when background is moving
	public Background(BufferedImage image, double x, double y, double moveSpeed, GamePanel panel) 
	{
		// call the main constructor to initialize everything
		this(image, x, y, moveSpeed, panel, MOVE);
	} // end constructor

	// constructor is private and initializes every single field and global variable
	private Background (BufferedImage image, double x, double y, double moveSpeed, GamePanel panel, int type)
	{
		this.image = image;				// store the image
		this.x = x;						// store x value
		this.y = y;						// store y value
		this.moveSpeed = moveSpeed;		// store moveSpeed
		this.panel = panel;				// store GamePanel object
		this.TYPE = type;				// store type
	} // end constructor

	// setPosition method sets the position of the background
	public void setPosition(double x, double y)
	{
		// only set position if the type is not still
		if (TYPE != STILL)
		{
			this.x = (x * moveSpeed);	// set the x value based on the moveSpeed
			this.y = (y * moveSpeed);	// set the y value based on the moveSpeed
		} // end if statement
	} // end setPosition method

	// draw method draws the background on the screen
	public void draw (Graphics g)
	{
		// check bounds if the background is moving. Based on that draw background
		if (TYPE == MOVE)
			checkBounds(g);
		else
			// if the image is still, draw the background image at x and y value
			g.drawImage(image, (int)x, (int)y, null);
	} // end draw method

	// checkBounds class will check if there is image left to draw and based on that update image
	private void checkBounds(Graphics g) 
	{
		// if the background image is out of screen on the left, 
		// draw the image by adding the panel's width. This will bring the image back from the other side
		if (x < 0)
		{
			// draw image
			g.drawImage(image,
					(int) x + panel.getWidth(), (int) y, null);
		}

		// draw the background image at x and y value
		g.drawImage(image, (int)x, (int)y, null);
	} // end checkBounds method

	public void setImage(BufferedImage image) 
	{
		this.image = image;
	} // end setImage method
} // end Background class
