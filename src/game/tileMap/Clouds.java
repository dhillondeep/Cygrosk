package game.tileMap;

import game.core.GamePanel;
import game.resources.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;

// this class controls the clouds background
public class Clouds extends Background
{
	// fields and global variables
	
	private BufferedImage [] clouds;		// stores the images of clouds
	
	// Constructor creates a clouds at certain position
	public Clouds(double x, double y, double moveSpeed, GamePanel panel) 
	{
		// call the super constructor
		super(null, x, y, moveSpeed, panel);
		
		// get the clouds images from Resources class
		clouds = new BufferedImage[]{Resources.getItems()[0], Resources.getItems()[1], 
				Resources.getItems()[2]};
		
		// create clouds image
		createClouds();
	} // end Constructor

	// createClouds method will create clouds image by joining multiple cloud images
	private void createClouds() 
	{
		// create a buffered image to be used as background image
		BufferedImage cImage = new BufferedImage(panel.getWidth(), panel.getHeight()/3, 
				BufferedImage.TYPE_INT_ARGB);
		
		// get graphics from the bufferedImage so that drawing can be done on it
		Graphics g = cImage.getGraphics();
		
		// draw first cloud
		g.drawImage(clouds[0], 60, 45, null);
		
		// draw second cloud
		g.drawImage(clouds[1], 315, 75, null);
		
		// draw third cloud
		g.drawImage(clouds[2], 595, 40, null);
		
		// draw fourth cloud
		g.drawImage(clouds[2], 820, 80, null);
		
		// draw fifth cloud
		g.drawImage(clouds[0], 1045, 50, null);
		
		// set the image in the parent class to the image just created
		image = cImage;
	} // end createClouds method
} // end Clouds class
