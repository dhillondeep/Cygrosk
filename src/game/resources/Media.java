package com.deep.game.resources;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.SwingWorker;

public class Media extends SwingWorker<Integer, Integer>
{

	// stores the resources for the game
	protected static BufferedImage [][][] playerSprites;		// stores player sprites
	protected static BufferedImage [][] tiles;					// stores tiles images
	protected static BufferedImage [][] enemies;				// stores enemies images
	protected static BufferedImage bg;							// stores background image
	protected static BufferedImage [] menu;						// store menu images
	protected static BufferedImage [] hud;						// stores HUD images
	protected static BufferedImage [] items;					// stores items images
	protected static BufferedImage [] play;						// stores play images
	protected static BufferedImage instructions;				// stores instructions image
	protected static BufferedImage gameOver;					// stores gameOver image
	protected static BufferedImage pause;						// stores pause image

	// when thread will start, this method will be called
	@Override
	public Integer doInBackground()
	{
		// load images for all the players
		loadSprites(Constants.TYPE_PLAYER_SPRITES);

		// load tiles images
		loadSprites(Constants.TYPE_TILES);

		// load images for enemies
		loadSprites(Constants.TYPE_ENEMIES);

		// load hud images
		loadSprites(Constants.TYPE_HUD);
		
		// load items images
		loadSprites(Constants.TYPE_ITEMS);

		// load background images
		bg = load(Constants.LOC_BG_1);

		// load menu screen images
		menu = new BufferedImage[Constants.MENU_IMAGES_NUM];
		
		menu[Constants.MENU_IMG1] = load(Constants.LOC_MENU0);
		menu[Constants.MENU_IMG2] = load(Constants.LOC_MENU1);
		menu[Constants.MENU_IMG3] = load(Constants.LOC_MENU2);
		menu[Constants.MENU_IMG4] = load(Constants.LOC_MENU3);
		
		// load play screen images
		play = new BufferedImage[Constants.PLAY_IMAGES_NUM];
		
		play[Constants.PLAYER1] = load(Constants.LOC_PLAY0);
		play[Constants.PLAYER2] = load(Constants.LOC_PLAY1);
		play[Constants.PLAYER3] = load(Constants.LOC_PLAY2);
		
		// load instructions image
		instructions = load(Constants.LOC_INST);
		
		// load game over image
		gameOver = load(Constants.LOC_GAMEOVER);
		
		// load pause image
		pause = load(Constants.LOC_GAMEPAUSE);
		
		// load enemy images
		return null;
	}

	// loadSprites method loads sub images adn put them in arrays
	private void loadSprites(int imageType)
	{
		// if player sprite images are selected
		if (imageType == Constants.TYPE_PLAYER_SPRITES)
		{
			// load the sprite sheet
			BufferedImage spriteSheet = load(Constants.LOC_PLAYER_SPRITESHEET);

			// initialize the array to store the images
			playerSprites = new BufferedImage[Constants.PLAYER_NUM][Constants.SPRITES_TYPE_NUM][];

			for (int player = 0; player < Constants.PLAYER_NUM; player++) 
			{
				// Initialize the array for each separate type of sprite for each player
				playerSprites[player][Constants.PLAYER_WALKING] = new 
						BufferedImage[Constants.SPRITES_WALKING_NUM];  	// walking sprite

				playerSprites[player][Constants.PLAYER_DUCKING] = new 
						BufferedImage[Constants.SPRITES_DUCKING_NUM]; 	// ducking sprite

				playerSprites[player][Constants.PLAYER_HURT] = new 
						BufferedImage[Constants.SPRITES_HURT_NUM]; 		// hurt sprite

				playerSprites[player][Constants.PLAYER_JUMPING] = new 
						BufferedImage[Constants.SPRITES_JUMPING_NUM];	// jumping sprite

				playerSprites[player][Constants.PLAYER_STANDING] = new 
						BufferedImage[Constants.SPRITES_STANDING_NUM];	// standing sprite
			}


			// go through the for loop so that all players' sprite can be obtained
			for (int player = 0; player < Constants.PLAYER_NUM; player++) 
			{
				// start with the current column position to 0
				int currColumn = 0;

				// temp variable
				int tempVal = currColumn;

				int counter = 0;

				// load walking sprite
				for (;currColumn < Constants.SPRITES_WALKING_NUM; currColumn++)
				{
					playerSprites[player][Constants.PLAYER_WALKING][counter] = 
							grabSprite(spriteSheet, Constants.SPRITESHEET_BOX_WIDTH, 
									Constants.SPRITESHEET_BOX_HEIGHT, player, currColumn, Constants.PLAYER_WIDTH, 
									Constants.PLAYER_HEIGHT);

					++counter;
				}


				// reset the counter
				counter = 0;

				tempVal = currColumn;

				// load ducking 
				for (;currColumn < (tempVal + Constants.SPRITES_DUCKING_NUM); currColumn++)
				{
					playerSprites[player][Constants.PLAYER_DUCKING][counter] = 
							grabSprite(spriteSheet, Constants.SPRITESHEET_BOX_WIDTH, 
									Constants.SPRITESHEET_BOX_HEIGHT, player, currColumn, Constants.PLAYER_WIDTH, 
									Constants.PLAYER_HEIGHT);

					++counter;
				}

				// reset the counter
				counter = 0;

				tempVal = currColumn;

				// load hurt
				for (;currColumn <  tempVal + Constants.SPRITES_HURT_NUM; currColumn++)
				{
					playerSprites[player][Constants.PLAYER_HURT][counter] = 
							grabSprite(spriteSheet, Constants.SPRITESHEET_BOX_WIDTH, 
									Constants.SPRITESHEET_BOX_HEIGHT, player, currColumn, Constants.PLAYER_WIDTH, 
									Constants.PLAYER_HEIGHT);

					++counter;
				}

				// reset the counter
				counter = 0;

				tempVal = currColumn;

				// load jumping
				for (;currColumn < tempVal + Constants.SPRITES_JUMPING_NUM; currColumn++)
				{
					playerSprites[player][Constants.PLAYER_JUMPING][counter] = 
							grabSprite(spriteSheet, Constants.SPRITESHEET_BOX_WIDTH, 
									Constants.SPRITESHEET_BOX_HEIGHT, player, currColumn, Constants.PLAYER_WIDTH, 
									Constants.PLAYER_HEIGHT);

					++counter;
				}

				// reset the counter
				counter = 0;

				tempVal = currColumn;

				// load standing
				for (;currColumn < tempVal + Constants.SPRITES_STANDING_NUM; currColumn++)
				{
					playerSprites[player][Constants.PLAYER_STANDING][counter] = 
							grabSprite(spriteSheet, Constants.SPRITESHEET_BOX_WIDTH, 
									Constants.SPRITESHEET_BOX_HEIGHT, player, currColumn, Constants.PLAYER_WIDTH, 
									Constants.PLAYER_HEIGHT);

					++counter;
				}
			}
		}
		// if tiles images are selected
		else if (imageType == Constants.TYPE_TILES)
		{
			// load the sprite sheet
			BufferedImage spriteSheet;

			// initialize the array to store tiles
			tiles = new BufferedImage[Constants.TILES_TYPES_NUM][];

			tiles[Constants.TILES_BLOCKED] = new BufferedImage[Constants.TILES_BLOCKED_NUM];
			tiles[Constants.TILES_UNBLOCKED] = new BufferedImage[Constants.TILES_UNBLOCKED_NUM];
			tiles[Constants.TILES_SPECIAL] = new BufferedImage[Constants.SPECIAL_NUM];

			// load blocked tiles
			spriteSheet = load(Constants.LOC_BLOCKED_TILES);

			int counter = 0;

			for (int row = 0; row < Constants.TILES_BLOCKED_ROWS_NUM; row++) 
			{
				for (int col = 0; col < Constants.TILES_BLOCKED_COL_NUM; col++) 
				{
					// only load resources if there is space in the array
					if (counter >= Constants.TILES_BLOCKED_NUM)
						break;

					tiles[Constants.TILES_BLOCKED][counter] =
							grabSprite(spriteSheet, Constants.TILES_BOX_WIDTH, Constants.TILES_BOX_HEIGHT, 
									row, col, Constants.TILES_BLOCKED_SPRITE_WIDTH, 
									Constants.TILES_BLOCKED_SPRITE_HEIGHT);

					++counter;
				}
			}

			// load unblocked tiles
			spriteSheet = load(Constants.LOC_UNBLOCKED_TILES);

			counter = 0;

			for (int row = 0; row < Constants.TILES_UNBLOCKED_ROWS_NUM; row++) 
			{
				for (int col = 0; col < Constants.TILES_UNBLOCKED_COL_NUM; col++) 
				{
					// only load resources if there is space in the array
					if (counter >= Constants.TILES_UNBLOCKED_NUM)
						break;

					tiles[Constants.TILES_UNBLOCKED][counter] =
							grabSprite(spriteSheet, Constants.TILES_BOX_WIDTH, Constants.TILES_BOX_HEIGHT, 
									row, col, Constants.TILES_UNBLOCKED_SPRITE_WIDTH, 
									Constants.TILES_UNBLOCKED_SPRITE_HEIGHT);
					++counter;
				}
			}

			// load special tiles
			spriteSheet = load(Constants.LOC_SPECIAL_TILES);

			counter = 0;

			for (int row = 0; row < Constants.SPECIAL_ROWS_NUM; row++) 
			{
				for (int col = 0; col < Constants.SPECIAL_COL_NUM; col++) 
				{
					// only load resources if there is space in the array
					if (counter >= Constants.SPECIAL_NUM)
						break;

					tiles[Constants.TILES_SPECIAL][counter] =
							grabSprite(spriteSheet, Constants.SPECIAL_BOX_WIDTH, Constants.SPECIAL_BOX_HEIGHT, 
									row, col, Constants.SPECIAL_SPRITE_WIDTH, 
									Constants.SPECIAL_SPRITE_HEIGHT[counter]);
					++counter;
				}
			}
		}
		// if enemies images are selected
		else if (imageType == Constants.TYPE_ENEMIES)
		{
			// load the sprite sheet
			BufferedImage spriteSheet = load(Constants.LOC_ENEMIES);

			enemies = new BufferedImage[Constants.NUM_OF_ENEMIES][Constants.NUM_OF_ENEMIES_FRAMES];

			int counter = 0;
			int row = 0;
			int col = 0;

			// load images
			for (int sheetRow = 0; sheetRow < Constants.NUM_OF_ROW_ENEMIES; sheetRow++) 
			{
				for (int sheetCol = 0; sheetCol < Constants.NUM_OF_COL_ENEMIES; sheetCol++) 
				{
					enemies[row][col] = grabSprite(spriteSheet, Constants.ENEMY_BOX_WIDTH,
							Constants.ENEMY_BOX_HEIGHT, sheetRow, sheetCol, Constants.ENEMIES_WIDTH[counter],
							Constants.ENEMIES_HEIGHT[counter]);

					++counter;
					++col;

					if (counter % 2 == 0)
					{
						++row;
						col = 0;
					}

				}
			}
		}
		// if HUD images are selected
		else if (imageType == Constants.TYPE_HUD)
		{
			// load the sprite sheet
			BufferedImage spriteSheet = load(Constants.LOC_HUD);
			
			// initialize array
			hud = new BufferedImage[Constants.HUD_TOTAL];
			
			// load images
			for (int count = 0; count < hud.length; count++) 
			{
				hud[count] = grabSprite(spriteSheet, Constants.HUD_BOX_WIDTH, Constants.HUD_BOX_HEIGHT, 
						0, count, Constants.HUD_WIDTH[count], Constants.HUD_HEIGHT[count]);
			}
		}
		// if items images are selected
		else if (imageType == Constants.TYPE_ITEMS)
		{
			// load the spite sheet
			BufferedImage spriteSheet = load(Constants.LOC_ITEMS);
			
			// initialize array
			items = new BufferedImage[Constants.ITEMS_TOTAL];
			
			// load images
			for (int count = 0; count < items.length; count++) 
			{
				items[count] = grabSprite(spriteSheet, Constants.ITEMS_BOX_WIDTH, Constants.ITEMS_BOX_HEIGHT, 
						0, count, Constants.ITEMS_WIDTH[count], Constants.ITEMS_HEIGHT[count]);
			}
		}
	} // end loadSprites method

	// grabSprite method grabs sub image from the big image
	private BufferedImage grabSprite(BufferedImage sheet, int boxWidth, int boxHeight, 
			int row, int col, int spriteWidth, int spriteHeight)
	{
		// get sub image based on the row and column
		return sheet.getSubimage((col * boxWidth), (row * boxHeight), spriteWidth, spriteHeight);
	} // end grabSprite method

	// load method loads the image and returns it
	private static BufferedImage load(String path)
	{
		// BufferedImage object
		BufferedImage temp = null;

		try
		{
			// read the image and store it in temp object
			temp = ImageIO.read(Media.class.getResourceAsStream(path));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		// return temp object
		return temp;
	} // end load method
} // end Media class
