package game.entity.liquids;

import game.entity.Liquid;
import game.resources.Resources;
import game.tileMap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;

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
