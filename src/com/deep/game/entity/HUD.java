package com.deep.game.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.deep.game.resources.Resources;

// this class handles the HUD screen in the game
public class HUD 
{
	// fields
	
	private Player player;				// player object
	private BufferedImage[][] images;	// images for each item that will be shown on HUD screen

	// constants
	private static final int COIN = 0;		// used to access coin image
	private static final int KEY = 1;		// used to access key image
	private static final int PLAYER = 2;	// used to access player images
	private static final int HEART = 3;		// used to access heart images
	private static final int TOTAL = 4;		// used to find how many items are on HUD screen

	// constructor loads up images
	public HUD(Player player) 
	{
		this.player = player;		// store player object

		// initialize images array
		images = new BufferedImage[TOTAL][];

		// load coin image from resources
		images[COIN] = new BufferedImage[]{Resources.getHUDImgs()[0]};
		// load key image from resources
		images[KEY] = new BufferedImage[]{Resources.getHUDImgs()[4]};
		// load player images from resources
		images[PLAYER] = new BufferedImage[]{Resources.getHUDImgs()[5], Resources.getHUDImgs()[6], 
				Resources.getHUDImgs()[7]};
		// load heart images from resources
		images[HEART] = new BufferedImage[]{Resources.getHUDImgs()[1], Resources.getHUDImgs()[2],
				Resources.getHUDImgs()[3]};
	} // end constructor
	
	// draw method draws HUD screen
	public void draw(Graphics g)
	{
		// draw hearts based on the health of the player
		for (int count = 0; count < player.getHealth(); count++) 
			g.drawImage(images[HEART][1], 10 + count * 53, 15, null);
		
		// draw player images based on the lives of the player
		for (int count = 0; count < player.getLives(); count++) 
			g.drawImage(images[PLAYER][player.getPlayerNum()], 10 + count * 53, 65, null);
		
		// draw an image of the coin
		g.drawImage(images[COIN][0], 870, 15, null);
		
		// show number of coins
		g.setColor(Color.MAGENTA);
		g.setFont(new Font("Arial", Font.BOLD, 50));
		g.drawString(getString(player.getCoins()), 930, 55);
		
		// draw an image of the key
		g.drawImage(images[KEY][0], 1000, 15, null);
		
		// show number of keys
		g.drawString(getString(player.getKeys()), 1060, 55);
		
		// show time elapsed
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.setColor(Color.BLUE);
		g.drawString(player.getTimeToString(), 1160, 50);
	} // end draw method
	
	// getString method returns the String object based on the integer value provided
	private String getString(int value) 
	{
		return "" + value;
	} // end getString method
} // end HUD class
