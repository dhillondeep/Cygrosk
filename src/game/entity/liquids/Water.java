package com.deep.game.entity.liquids;

import java.awt.Point;
import java.awt.image.BufferedImage;

import com.deep.game.entity.Liquid;
import com.deep.game.resources.Resources;
import com.deep.game.tileMap.TileMap;

// this class controls the behavior of water on the screen
public class Water extends Liquid
{
	// constructor create Water on the screen
	public Water(TileMap map, Point location, int numRows, int numCols)
	{
		// call the parent constructor
		super(map, location, numCols, numCols);
		
		// set the images for the creation of water
		setImages(new BufferedImage[]{Resources.getSpecialTilesImgs()[10], 
				Resources.getSpecialTilesImgs()[9]});
		
		// construct the main water image and set it as a draw image
		setDrawImage(constructImage());
	} // end constructor
} // end Water class
