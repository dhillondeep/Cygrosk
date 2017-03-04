package com.deep.game.entity.liquids;

import java.awt.Point;
import java.awt.image.BufferedImage;

import com.deep.game.entity.Liquid;
import com.deep.game.resources.Resources;
import com.deep.game.tileMap.TileMap;

// this class controls the behavior of lava on the screen
public class Lava extends Liquid
{
	// constructor create Lava on the screen
	public Lava(TileMap map, Point location, int numRows, int numCols)
	{
		// call the parent constructor
		super(map, location, numCols, numCols);
		
		// set the images for the creation of lava
		setImages(new BufferedImage[]{Resources.getSpecialTilesImgs()[8], 
				Resources.getSpecialTilesImgs()[7]});
		
		// construct the main lava image and set it as a draw image
		setDrawImage(constructImage());
	} // end constructor
} // end Lava class
