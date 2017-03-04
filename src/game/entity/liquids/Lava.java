package game.entity.liquids;

import game.entity.Liquid;
import game.resources.Resources;
import game.tileMap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;

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
