package game.entity;

import game.core.GamePanel;
import game.tileMap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;

// this class is the base class for all enemies
public abstract class Enemy extends MapObject
{
	// fields and global variables

	private BufferedImage [] sprites;		// array stores the images for animation

	private int [] widths;					// array to store widths for each frame
	private int [] heights;					// array to store heights for each frame
	protected int blockageWidth;			// width used to check collisions

	private double min;						// minimum x value an enemy can reach
	private double max;						// maximum x value an enemy can reach
	private boolean isBlocked;				// boolean to check if something is blocked on tile map

	// constructor initializes fields and global variables
	public Enemy(TileMap tm, GamePanel panel, double min, double max) 
	{
		// call the parent constructor
		super(tm, panel);

		this.min = min;		// set the minimum value for the enemy
		this.max = max;		// set the maximum value for the enemy
	} // end constructor

	// blockRestrictions method blocks the enemy from going past the restrictions
	public void blockRestrictions()
	{
		// negative max or/and min means there is no max or/and min

		// check if the enemy's x value is less than or equal to min
		if (min >= 0 && getLX() <= min)
		{
			isBlocked = true;		// set isBlocked true
			dx = 0;					// stop the enemy by making dx 0
		}
		// check if the enemy's (x + blockageWidth) is greater than or equal to max
		else if (max >= 0 && getLX() + blockageWidth >= max)
		{
			isBlocked = true;		// set isBlocked true
			dx = 0;					// stop the enemy by making dx 0
		}
		// if the enemy is not touching any restrictions, make isBlocked false
		else
			isBlocked = false;
	} // end blockRestrictions method

	// update method is unique for every enemy and every enemy should have this method
	public abstract void update();

	// updateDimensions method updates the width and height of the enemy based on the animation playing
	public void updateDimensions() 
	{
		width = widths[animation.getCurrentFrame()];
		height = heights[animation.getCurrentFrame()];
	} // end updateDimensions method

	// isBlocked method returns true or false based on if something is blocked on map
	public boolean isBlocked() 
	{
		return isBlocked;
	} // end getBlocked

	// getSprites method returns the images for the enemy
	public BufferedImage[] getSprites() 
	{
		return sprites;
	} // end getSprites method

	// setFacingLeft method sets the direction of the player to left
	public void setFacingLeft()
	{
		// the sprites for enemies are all facing left so, making isFacingRight true actually makes
		// the enemy face left
		isFacingRight = true;
	} // end setFacingLeft method

	// setFacingRight method sets the direction of the player to right
	public void setFacingRight()
	{
		// the sprites for enemies are all facing left so, making isFacingRight false actually makes 
		// the enemy face right
		isFacingRight = false;
	} // end setFacingRight method

	// setWidths method sets the widths for the enemy
	public void setWidths(int[] widths) 
	{
		this.widths = widths;
	} // end setWidths method

	// setHeights method sets the heights for the enemy
	public void setHeights(int[] heights) 
	{
		this.heights = heights;
	} // end setHeights method

	// setSprites method sets the images for the enemy
	public void setSprites(BufferedImage[] sprites) 
	{
		this.sprites = sprites;
	} // end setSprites method

	// setPosition method sets the position of the enemy using the point given
	public void setPosition(Point p) 
	{
		// call the parent setPosition method
		setPosition(p.getX(), p.getY());
	} // end setPosition method
} // end Enemy classs
