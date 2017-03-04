package com.deep.game.entity.items;

import java.awt.image.BufferedImage;

import com.deep.game.core.GamePanel;
import com.deep.game.entity.Item;
import com.deep.game.resources.Constants;
import com.deep.game.tileMap.TileMap;

// this class controls the behavior of coins on the screen
public class Coin extends Item
{
	// constructor creates a coin
	public Coin(TileMap tm, GamePanel panel, BufferedImage image) 
	{
		// call the parent constructor
		super(tm, panel, Constants.COIN);
		
		// set the image of the coin based on the image provided
		setImage(image);
		
		// set the frames for the animation
		animation.setFrames(new BufferedImage[]{getImage()});
		// since there is only one image in the animation, set the delay to -1
		animation.setDelay(-1);
		
		width = 36;			// set the width of the coin to 36
		height = 36;		// set the height of the coin to 36
	} // end constructor
} // end Coin class
