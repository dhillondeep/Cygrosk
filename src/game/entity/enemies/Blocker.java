package game.entity.enemies;

import game.core.GamePanel;
import game.entity.Enemy;
import game.resources.Constants;
import game.resources.Resources;
import game.tileMap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;

// this class controls Blocker enemy stuff
public class Blocker extends Enemy
{
	// fields and global variables
	
	private BufferedImage image;		// image to be used in animation
	
	// constructor creates blocker enemy 
	public Blocker(TileMap tm, GamePanel panel, int size, double min, double max) 
	{
		// call the parent constructor
		super(tm, panel, min, max);
		
		width = 51;					// set width of the blocker
		height = 51 * size;			// set height of the blocker
		fallSpeed = 0.2;			// set fall speed of the blocker
		maxFallSpeed = 10.0;		// set maximum fall speed of the blocker
		
		// get blocker images from the resources and set those images as sprites
		setSprites(Resources.getEnemyImgs(Constants.BLOCKER));
		
		// construct an image for the blocker and set image equal to this image
		image = constructImage(size);
		
		// set frames for the animation as image constructed
		animation.setFrames(new BufferedImage[]{image});
		// set the delay to -1 so that only one frame is drawn
		animation.setDelay(-1);
	} // end Constructor

	// constructImage method creates an image based on the size
	private BufferedImage constructImage(int size) 
	{
		// create a buffered image based on width and height
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		// get the graphics from the image so that something can be drawn on the image
		Graphics g = img.getGraphics();
		
		// run the for loop based on the size
		for (int i = 0; i < size; i++) 
		{
			// always draw the top part as the sprite 1 image
			if (i == 0)
				g.drawImage(getSprites()[1], 0, 0, null);
			// after that, keep on drawing the sprite 0 image based on the size
			else
				g.drawImage(getSprites()[0], 0, (getSprites()[0].getHeight() * i), null);
		} // end for loop
		
		// return the image when drawing is done
		return img;
	} // end constructImage method

	// update method updates the blocker existence on the tileMap
	@Override
	public void update() 
	{
		// check if the blocker is falling
		if (isFalling)
			// if blocker is falling, make it fall by adding fallSpeed
			dy += fallSpeed;
		
		// check tile map collision for the blocker
		checkTileMapCollision();

		// set the position of the blocker based on temporary x and temporary y
		setPosition(xTemporary, yTemporary);
	} // end update method
} // end Blocker class