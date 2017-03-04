package game.resources;

import java.awt.image.BufferedImage;

// this class keeps track of all game resources
public abstract class Resources
{
	// method to get player's walking images
	public static BufferedImage [] getWalkingImgs(int playerNum)
	{
		return Media.playerSprites[playerNum][Constants.PLAYER_WALKING];
	}

	// method to get the ducking images for the player
	public static BufferedImage [] getDuckingImgs(int playerNum)
	{
		return Media.playerSprites[playerNum][Constants.PLAYER_DUCKING];
	}

	// method to get the hurt images for the player
	public static BufferedImage [] getHurtImgs(int playerNum)
	{
		return Media.playerSprites[playerNum][Constants.PLAYER_HURT];
	}

	// method to get the jumping images for the player
	public static BufferedImage [] getJumpingImgs(int playerNum)
	{
		return Media.playerSprites[playerNum][Constants.PLAYER_JUMPING];
	}

	// method to get the standing images for the player
	public static BufferedImage [] getStandingImgs(int playerNum)
	{
		return Media.playerSprites[playerNum][Constants.PLAYER_STANDING];
	}

	// method to get menu screen images
	public static BufferedImage [] getMenuImgs()
	{
		return Media.menu;
	}
	
	// method to get menu play images
	public static BufferedImage [] getPlayImgs()
	{
		return Media.play;
	}
	
	// method to get instructions image
	public static BufferedImage getInstructionsImg()
	{
		return Media.instructions;
	}
	
	// method to get game over image
	public static BufferedImage getGameOverImg()
	{
		return Media.gameOver;
	}
	
	// method to get pause image
	public static BufferedImage getPauseImg()
	{
		return Media.pause;
	}

	// method to get blocked tiles images
	public static BufferedImage [] getBlockedTilesImgs()
	{
		return Media.tiles[Constants.TILES_BLOCKED];
	}

	// method to get unblocked tiles images
	public static BufferedImage [] getUnblockedTilesImgs()
	{
		return Media.tiles[Constants.TILES_UNBLOCKED];
	}

	// method to get special tiles images
	public static BufferedImage [] getSpecialTilesImgs()
	{
		return Media.tiles[Constants.TILES_SPECIAL];
	}

	// method to get background image
	public static BufferedImage getBackgroundImg()
	{
		return Media.bg;
	}
	
	// method to get enemy images
	public static BufferedImage [] getEnemyImgs(int enemyNum)
	{
		return Media.enemies[enemyNum];
	}
	
	// method to get HUD images
	public static BufferedImage [] getHUDImgs()
	{
		return Media.hud;
	}
	
	// method to get items images
	public static BufferedImage [] getItems()
	{
		return Media.items;
	}
} // end Resources class
