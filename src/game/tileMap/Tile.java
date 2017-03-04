package game.tileMap;

import java.awt.image.BufferedImage;

// this class is a tile on the tile map
public class Tile
{
	// fields and global variables
	
	// graphics related
	private final BufferedImage IMAGE;			// stores the image of the tile	
	private final int TYPE;						// stores the type of the tile
	
	// constants
	public static final int UNBLOCKED = 0;		// constant for unblocked tiles
	public static final int BLOCKED = 1;		// constant for blocked tiles
	
	// constructor initializes fields and global variables
	public Tile(BufferedImage image, int type)
	{
		this.IMAGE = image;			// store image
		this.TYPE = type;			// store type
	} // end constructor
	
	// getImage method returns the image for the tile
	public BufferedImage getImage()
	{
		return IMAGE;
	} // end getImage method
	
	// getType method returns the type of the tile
	public int getType()
	{
		return TYPE;
	} // end getType method
} // end Tile class
