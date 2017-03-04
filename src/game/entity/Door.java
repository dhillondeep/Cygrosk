package com.deep.game.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.deep.game.handlers.Keyboard;
import com.deep.game.resources.Resources;
import com.deep.game.tileMap.TileMap;

// this class handles the door on the tile map
public class Door 
{
	// fields and global variables 

	private Player player;				// player object
	private TileMap map;				// tileMap object
	private BufferedImage currImage;	// current image of the door
	private BufferedImage [] images;	// images for the door
	private int doorStatus;				// status of the door
	private boolean shouldOpen;			// boolean to decide if door should open or not
	private boolean gameFinished;		// boolean to decide if game is finished or not

	private int x;						// x position of the door
	private int y;						// y position of the door
	private int width;					// width of the door
	private int height;					// height of the door

	// constants
	public static final int CLOSED = 0;		// door closed constant
	public static final int OPEN = 1;		// door open constant

	// constructor creates a door
	public Door(TileMap map, Player player, int x, int y, int width, int height) 
	{
		this.player = player;			// stores the player object
		this.map = map;					// stores the tileMap object
		this.x = x;						// stores the x coordinate of the door
		this.y = y;						// stores the y coordinate of the door
		this.width = width;				// stores the width of the door
		this.height = height;			// stores the height of the door

		// get images from the resources and store them in images array
		images = new BufferedImage[]{Resources.getSpecialTilesImgs()[5], 
				Resources.getSpecialTilesImgs()[6]};

		doorStatus = CLOSED;				// set the status of the door to be closed
		currImage = images[doorStatus];		// set the current image of the door based on the status
		shouldOpen = false;					// make shouldOpen false since door cannot be opened
		gameFinished = false;				// make gameFinished false since door game is not finished yet
	} // end constructor

	// update method updates the door
	public void update() 
	{
		// handle inputs so that the door can be opened
		inputHandler();

		// check if door should be opened and it contains the player
		if (shouldOpen && containsPlayer())
		{
			// if true, check if player has collected a key
			if (player.getKeys() > 0) 
				// if key is collected, change the door status to open
				doorStatus = OPEN;
			else
				// if key is not collected, change the door status to closed
				doorStatus = CLOSED;
		}
		// if door should not open or/ and it does not contain the player
		else
			// make the door status closed
			doorStatus = CLOSED;

		// set the current image of the door based on the door status
		currImage = images[doorStatus];
	} // end update method

	// draw method draws the door based on the current image
	public void draw(Graphics g)
	{
		// x and y value will be changed based on how tile map is moved
		g.drawImage(currImage, (int) (x + map.getX()), (int) (y + map.getY()), null);
	} // end draw method

	// getDoorStatus method returns true or false based on if door is open or closed 
	public int getDoorStatus() 
	{
		return doorStatus;
	} // end getDoorStatus method

	// contains method checks if door object contains the player
	public boolean containsPlayer() 
	{
		// get the rectangle object for the door
		Rectangle doorObj = getRectangle();
		// get the rectangle object for the player object
		Rectangle playerObj = player.getRectangle();

		// return a boolean value based on if door contains player
		return doorObj.contains(playerObj);
	} // end contains method

	// getRectangle method return a rectangle object
	public Rectangle getRectangle()
	{
		// create a rectangle using the door's dimensions and return that
		return new Rectangle
				(
						x,
						y,
						width,
						height
						);
	} // end getRectangle method

	// inputHandler method handles keyboard inputs
	private void inputHandler() 
	{
		// if user pressed 'O' key, make shouldOpen boolean true
		if (Keyboard.isKeyPressed(Keyboard.O))
			shouldOpen = true;
		else
			// if that key is not pressed, make shouldOpen boolean false
			shouldOpen = false;

		// if game is finished, keep the door open no matter if user is pressing 'O' or not
		if (gameFinished)
			// make shouldOpen true
			shouldOpen = true;
	} // end inputHandler method

	// setGameFinished method sets gameFinished true or false
	public void setGameFinished(boolean gameFinished) 
	{
		this.gameFinished = gameFinished;
	} // end setGameFinished method
} // end Door class