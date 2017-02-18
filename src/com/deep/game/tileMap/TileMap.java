package com.deep.game.tileMap;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Scanner;

import com.deep.game.core.GamePanel;
import com.deep.game.resources.Constants;
import com.deep.game.resources.Resources;

// this class creates tiles and draw them on screen
public class TileMap 
{
	// declare fields and global variables

	// position related
	private double x;					// x position of the tile map
	private double y;					// y position of the tile map

	// bounds related
	private int xMin;					// minimum x value for the map
	private int yMin;					// minimum y value for the map
	private int xMax;					// maximum x value for the map
	private int yMax;					// maximum y value for the map

	// tile map attributes related
	private int[][] mapValue;			// mapValue array to store value of each tile
	private int tileSize;				// size of the single tile
	private int numRows;				// number of rows on the tile map
	private int numCols;				// number of columns on the tile map
	private int mapWidth;				// width of the map
	private int mapHeight;				// height of the map
	private Tile[] tiles;				// array of Tile class to store every tile as an object

	// drawing related
	private double smoother;			// variable to smoothen the tile map movement
	private int rowOffset;				// off set value for rows
	private int colOffset;				// off set value for columns
	private int numRowsToDraw;			// number of rows to draw from tile map
	private int numColsToDraw;			// number of columns to draw from the tile map
	private final int extra;			// extra rows and columns to draw in order to avoid any weird drawing
	private GamePanel panel;			// object of the GamePanel class


	// constructor initializes tileSize and provides it the gamePanel object
	public TileMap(int tileSize, double x, double y, GamePanel panel) 
	{
		this.panel = panel;				// store the gamePanel object
		this.tileSize = tileSize;		// store the size of the tile
		this.extra = 2;					// extra rows and columns to draw
		this.smoother = 1;				// set the smoother value to 1
		this.x = x;						// set the x value
		this.y = y;						// set the y value

		// find number of rows to draw by dividing the panel height by tileSize
		// add extra to rows
		numRowsToDraw = (panel.getHeight() / tileSize) + extra;

		// find number of columns to draw by dividing the panel width by tileSize
		// add extra to columns
		numColsToDraw = (panel.getWidth() / tileSize) + extra;
	} // end constructor

	// loadTiles method will load tiles 
	public void loadTiles() 
	{
		// initialize the tiles array
		tiles = new Tile[Constants.TILES_NUM];

		// use counter variable to add tiles to the array
		int counter = 0;

		// go through the blocked tiles and add them to the tiles array
		for (int count = 0; count < Constants.TILES_BLOCKED_NUM; count++)
		{
			// create a new Tile object and add a blocked image and set its type to blocked
			tiles[counter] = new Tile(Resources.getBlockedTilesImgs()[count], Tile.BLOCKED);
			// increment counter
			++counter;
		} // end for loop

		// go through the unblocked tiles and add them to the tiles array
		for (int count = 0; count < Constants.TILES_UNBLOCKED_NUM; count++)
		{
			// create a new Tile object and add a unblocked image and set its type to unblocked
			tiles[counter] = new Tile(Resources.getUnblockedTilesImgs()[count], Tile.UNBLOCKED);
			// increment counter
			++counter;
		} // end for loop
	} // end loadTiles method

	// loadMap method will be used to load the map for the game based on the file location provided
	public void loadMap(String s) {

		// get an input stream from the map file stored in resources
		InputStream is = this.getClass().getResourceAsStream(s);

		// create a Scanner to read the file
		Scanner sc = new Scanner(is);

		try 
		{
			numRows = Integer.parseInt(sc.nextLine());	// first line contains number of rows
			numCols = Integer.parseInt(sc.nextLine());	// second line contains number of columns

			// create a mapValue array based on number of rows and number of columns 
			mapValue = new int[numRows][numCols];

			// find the width of the map
			mapWidth = numCols * tileSize;

			// find the height of the map
			mapHeight = numRows * tileSize;

			// xMin will be panel's width - mapWidth. This done so that map does not move if
			// it has nothing to the right
			xMin = panel.getWidth() - mapWidth;
			// xMax will be 0. This is done so that map does not move if it has nothing to the left
			xMax = 0;
			// xMin will be panel's height - mapHeight. This done so that map does not move if
			// it has nothing to the bottom
			yMin = panel.getHeight() - mapHeight;
			// yMax will be 0. This is done so that map does not move if it has nothing to the top
			yMax = 0;

			// delimiter to be used to separate the line tokens
			String delims = "\\s+";

			// go through the rows
			for(int row = 0; row < numRows; row++) 
			{
				// read the line and store it
				String line = sc.nextLine();
				// divide it into tokens
				String[] tokens = line.split(delims);

				// go through all the columns in the row
				for(int col = 0; col < numCols; col++) 
				{
					int value = Integer.parseInt(tokens[col]);	// find the value by parsing the String
					mapValue[row][col] = value;					// store the value in the mapValue array
				} // end for loop
			} // end for loop
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}

		// close the scanner
		sc.close();
	} // end loadMap method


	// getType method returns the type of the tile at certain row and column
	public int getType(int row, int col) 
	{
		int currMapValue = mapValue[row][col]; // store current map value from the array
		
		// return the type of the tile at that index
		return tiles[currMapValue].getType();
	} // end getType method

	// draw method draws tile map on the screen
	public void draw(Graphics g) 
	{
		// only draw rows that are on screen
		// this is done by starting at rowOffSet and going until rowOffSet plus number of rows to draw on screen
		for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++) 
		{
			// if maximum rows have been reached, break out of the loop
			if(row >= numRows) break;

			// only draw columns that are on screen
			// this is done by starting at colOffSet and going until colOffSet 
			// plus number of columns to draw on screen
			for(int col = colOffset; col < colOffset + numColsToDraw; col++) 
			{
				// if maximum columns have been reached, break out of the loop
				if(col >= numCols) break;

				// get the current value of the row and column from the map
				int currMapValue = mapValue[row][col];

				// get the image of the tile which has an index of currMapValue
				BufferedImage image = tiles[currMapValue].getImage();

				// draw the image based on the column, row, x and y values
				g.drawImage(
						image,
						(int)x + col * tileSize,
						(int)y + row * tileSize,
						null
						);
			} // end for loop
		} // end for loop
	} // end draw method
	
	// fixMapPosition method will not let the tileMap move beyond its minimum and maximum x and y value
	// this is done so tile map always stays on the screen even if it moves
	public void fixMapPosition() 
	{
		// this is for the ending
		if(x < xMin) x = xMin;	// set the x value equal to xMin if it goes any less
		// this is for the starting
		if(y < yMin) y = yMin;	// set the y value equal to yMin if it goes any less
		if(x > xMax) x = xMax;	// set the x value equal to xMax if it goes any more
		if(y > yMax) y = yMax;	// set the y value equal to yMax if it goes any more
	} // end fixMapPosition

	
	/////////////////      getters          ////////////////

	// getTileSize method will return the size of the tile
	public int getTileSize() 
	{ 
		return tileSize; 
	} // end getTileSize method

	// getX method will return the x position of the tileMap
	public double getX() 
	{
		return x; 
	} // end getX method

	// getY method will return the y position of the tileMap
	public double getY() 
	{
		return y; 
	} // end getY method

	// getWidth method will return the width of the tileMap 
	public int getWidth() 
	{ 
		return mapWidth; 
	} // end getWidth method

	// getHeight method will return the height of the tileMap
	public int getHeight() 
	{ 
		return mapHeight; 
	} // end getHeight method

	// getNumRows method will return the number of rows on the tileMap
	public int getNumRows() 
	{ 
		return numRows; 
	} // end getNumRows method

	// getNumCols method will return the number of columns on the tileMap
	public int getNumCols() 
	{ 
		return numCols; 
	} // end getNumCols method


	/////////////////      setters          ////////////////

	// setSmoother method sets the value of the smoother
	public void setSmoother(double d) 
	{
		smoother = d; 
	} // end setSmoother
	
	// setPosition method sets the position of the tile map
	public void setPosition(double x1, double y1) 
	{
		this.x += (x1 - this.x) * smoother;	// add the new value to the previous value. The counter goes negative
		this.y += (y1- this.y) * smoother;	// add the new value to the previous value. The counter goes negative
		
		// check if map has enough tiles left to show. Based on that fix x and y
		fixMapPosition();

		// find the column offset by dividing x by tileSize. This will give us how many columns the map has moved
		// has to be positive so, change the sign
		colOffset = (int) -this.x / tileSize;
		// find the row offset by diving y by tileSize. This will give us how many rows the map has moved
		// has to be positive so, change the sign
		rowOffset = (int) -this.y / tileSize;
	} // end setPosition method
} // end TileMap class
