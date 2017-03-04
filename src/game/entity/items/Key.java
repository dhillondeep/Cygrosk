package game.entity.items;

import game.core.GamePanel;
import game.entity.Item;
import game.resources.Constants;
import game.tileMap.TileMap;

import java.awt.image.BufferedImage;

// this class controls the behavior of keys on the screen
public class Key extends Item
{
	// constructor creates a key
	public Key(TileMap tm, GamePanel panel, BufferedImage image) 
	{
		// call the parent constructor
		super(tm, panel, Constants.KEY);
		
		// set the image of the key based on the image provided
		setImage(image);
		
		// set the frames for the animation
		animation.setFrames(new BufferedImage[]{getImage()});
		// since there is only one image in the animation, set the delay to -1
		animation.setDelay(-1);
		
		width = 60;			// set the width of the key to 60
		height = 36;		// set the height of the key to 36
	} // end constructor
} // end Key class
