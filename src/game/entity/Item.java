package com.deep.game.entity;

import java.awt.Point;
import java.awt.image.BufferedImage;

import com.deep.game.core.GamePanel;
import com.deep.game.tileMap.TileMap;

// this class is the base class for all the items
public class Item extends MapObject
{
	// fields 
	
	private BufferedImage image;		// image of the item
	private int type;					// type of the item
	
	// constructor initialize fields
	public Item(TileMap tm, GamePanel panel, int type) 
	{
		// call parent class
		super(tm, panel);
		
		// set the type based on the type provided
		this.type = type;
	} // end constructor

	// getImage method returns the image of the item
	public BufferedImage getImage() 
	{
		return image;
	} // end getImage method
	
	// getType method returns the type of the item
	public int getType() 
	{
		return type;
	} // end getType method
	
	// setImage method sets the image of the item
	public void setImage(BufferedImage image) 
	{
		this.image = image;
	} // end setImage method
	
	// setPosition method sets the position of the item
	public void setPosition(Point p) 
	{
		// convert the point into x and y and call the setPosition method in parent clas
		setPosition(p.getX() + width/2, p.getY() + height/2);
	} // end setPosition method
} // end Item class
