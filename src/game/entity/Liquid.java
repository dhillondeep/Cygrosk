package game.entity;

import game.tileMap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;

// this class is the base class for all the liquids
public class Liquid
{
	// fields and global variables
	
	private BufferedImage [] images;		// array to store image for liquid
	private BufferedImage drawImage;		// drawImage is the actually image that is drawn on the screen
	protected final double x;				// x coordinate of the liquid
	protected final double y;				// y coordinate of the liquid
	protected final int numRows;			// number of rows to draw
	protected final int numCols;			// number of columns to draw
	protected final int tileSize;			// tile size for the tile map
	protected int width;					// width of the liquid
	protected int height;					// height of the liquid
	protected final TileMap map;			// TileMap object

	// constructor create a liquid
	public Liquid(TileMap map, Point location, int numRows, int numCols)
	{
		this.x = location.getX();				// set the x coordinate
		this.y = location.getY();				// set the y coordinate
		this.numRows = numRows;					// set number of rows
		this.numCols = numCols;					// set number of columns
		this.tileSize = map.getTileSize();		// get tile size and store it
		this.map = map;							// store the tileMap object
		
		width = numCols * tileSize;				// find the width of the liquid and then store it
		height = numRows * tileSize;			// find the height of the liquid and then store it
	} // end constructor
	
	// draw method draws the image of liquid on screen
	public void draw(Graphics g)
	{
		g.drawImage(getDrawImage(), (int)(x + map.getX()), (int)(y + map.getY()), null);
	} // end draw method
	
	// contains method check if liquid contains any map object
	public boolean contains(MapObject o)
	{
		Rectangle object = o.getRectangle();	// get rectangle for the map object 	
		Rectangle liquid = getRectangle(); 		// get rectangle for the liquid
		
		// return true or false based on liquid contains map object
		return liquid.contains(object);
	} // end contains method
	
	// getRectangle method returns a rectangle for liquid
	public Rectangle getRectangle()
	{
		// create a rectangle using the liquid's dimensions and return that
		return new Rectangle
				(
						(int) x,
						(int) y,
						width,
						height
						);
	} // end getRectangle method
	
	// constructImage method creates an image of the liquid
	protected BufferedImage constructImage() 
	{
		// create an image for the liquid using width and height
		BufferedImage img = new BufferedImage(width, height, 
				BufferedImage.TYPE_INT_ARGB);
		
		// get graphics from the image so that something can be drawn on it
		Graphics g = img.getGraphics();
		
		// go through all the rows for the liquid
		for (int row = 0; row < numRows; row++) 
		{
			// go through all the columns for the liquid
			for (int col = 0; col < numCols; col++) 
			{
				// if it is the first row, draw first image of the liquid
				if (row == 0)
					g.drawImage(images[0], col * tileSize, row * tileSize, null);
				// if it is any other column, draw second image of the liquid
				else
					g.drawImage(images[1], col * tileSize, row * tileSize, null);
			} // end for loop
		} // end for loop
		
		// return the image 
		return img;
	} // end constructImage method
	
	// getDrawImage method returns the draw image for the liquid
	public BufferedImage getDrawImage() 
	{
		return drawImage;
	} // end getDrawImage method
	
	// setImages method sets the images for the liquid
	public void setImages(BufferedImage[] images) 
	{
		this.images = images;
	} // end setImages method
	
	// setDrawImage method sets the draw image
	public void setDrawImage(BufferedImage drawImage) 
	{
		this.drawImage = drawImage;
	} // end setDrawImage method
} // end Liquid class
