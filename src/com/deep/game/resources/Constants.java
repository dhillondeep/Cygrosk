package com.deep.game.resources;

// this class contains various constants used in the game
public abstract class Constants
{
	// constants below stores the address to the images
	public static final String LOC_BLOCKED_TILES = "/tiles/blocked_tiles.png";
	public static final String LOC_UNBLOCKED_TILES = "/tiles/unblocked_tiles.png";
	public static final String LOC_SPECIAL_TILES = "/tiles/special_tiles.png";
	public static final String LOC_PLAYER_SPRITESHEET = "/sprites/players/players_spritesheet.png";
	public static final String LOC_ENEMIES = "/sprites/enemies/spriteSheet.png";
	public static final String LOC_INTRO = "/intro_screen/intro.gif";
	public static final String LOC_MAP1 = "/maps/map_1.txt";
	public static final String LOC_BG_1 = "/background/bg_1.png";
	public static final String LOC_HUD = "/hud/hud.png";
	public static final String LOC_ITEMS = "/items/items.png";
	public static final String LOC_MENU0 = "/others/menu_screen0.png";
	public static final String LOC_MENU1 = "/others/menu_screen1.png";
	public static final String LOC_MENU2 = "/others/menu_screen2.png";
	public static final String LOC_MENU3 = "/others/menu_screen3.png";
	public static final String LOC_PLAY0 = "/others/pickPlayer0.png";
	public static final String LOC_PLAY1 = "/others/pickPlayer1.png";
	public static final String LOC_PLAY2 = "/others/pickPlayer2.png";
	public static final String LOC_INST = "/others/instructions.png";
	public static final String LOC_GAMEOVER = "/others/gameOver.png";
	public static final String LOC_GAMEPAUSE = "/others/gamePaused.png";
	
	// menu constants
	public static final int MENU_IMAGES_NUM = 4;
	public static final int MENU_IMG1 = 0;
	public static final int MENU_IMG2 = 1;
	public static final int MENU_IMG3 = 2;
	public static final int MENU_IMG4 = 3;
	public static final int PLAY_IMAGES_NUM = 3;
	public static final int INST_IMAGES_NUM = 1;
	public static final int GAMEOVER_IMAGES_NUM = 1;
	public static final int PAUSE_IMAGES_NUM = 1;
	
	
	// map constants
	public static final int TOTAL_MAPS = 1;
	public static final int MAP1 = 0;
	
	
	// player sprite constants
	public static final int SPRITES_TYPE_NUM = 5;
	public static final int SPRITES_WALKING_NUM = 11;
	public static final int SPRITES_DUCKING_NUM = 1;
	public static final int SPRITES_HURT_NUM = 1;
	public static final int SPRITES_JUMPING_NUM = 1;
	public static final int SPRITES_STANDING_NUM = 1;
	public static final int SPRITESHEET_BOX_WIDTH = 75;
	public static final int SPRITESHEET_BOX_HEIGHT = 75;
	
	// tiles and tile map constants
	public static final int TILES_NUM = 116;
	public static final int TILES_SIZE = 70;
	public static final int TILES_BOX_WIDTH = 71;
	public static final int TILES_BOX_HEIGHT = 71;
	public static final int TILES_TYPES_NUM = 3;
	public static final int TILES_BLOCKED = 0;
	public static final int TILES_UNBLOCKED = 1;
	public static final int TILES_SPECIAL = 2;
	
	// blocked tiles constants
	public static final int TILES_BLOCKED_NUM = 102;
	public static final int TILES_BLOCKED_SPRITE_WIDTH = 70;
	public static final int TILES_BLOCKED_SPRITE_HEIGHT = 70;
	public static final int TILES_BLOCKED_ROWS_NUM = 11;
	public static final int TILES_BLOCKED_COL_NUM = 10;
	
	// unblocked tiles constants
	public static final int TILES_UNBLOCKED_NUM = 14; // air counts as empty
	public static final int TILES_UNBLOCKED_SPRITE_WIDTH = 70;
	public static final int TILES_UNBLOCKED_SPRITE_HEIGHT = 70;
	public static final int TILES_UNBLOCKED_ROWS_NUM = 3;
	public static final int TILES_UNBLOCKED_COL_NUM = 5;
	
	// special items
	public static final int SPECIAL_NUM = 15;
	public static final int SPECIAL_BOX_WIDTH = 71;
	public static final int SPECIAL_BOX_HEIGHT = 111;
	public static final int SPECIAL_SPRITE_WIDTH = 70;
	public static final int [] SPECIAL_SPRITE_HEIGHT = {70, 70, 70, 70, 70, 110, 110, 
														70, 70, 70, 70, 70, 70, 70, 70};
	public static final int SPECIAL_ROWS_NUM = 3;
	public static final int SPECIAL_COL_NUM = 5;
	
	// player constants
	public static final int PLAYER_WIDTH = 50;
	public static final int PLAYER_HEIGHT = 70;
	public static final int PLAYER_NUM = 3;
	public static final int PLAYER1 = 0;
	public static final int PLAYER2 = 1;
	public static final int PLAYER3 = 2;
	public static final int PLAYER_ANIMATION_NUM = 5;
	public static final int PLAYER_WALKING = 0;
	public static final int PLAYER_DUCKING = 1;
	public static final int PLAYER_HURT = 2;
	public static final int PLAYER_JUMPING = 3;
	public static final int PLAYER_STANDING = 4;

	
	// background constants
	public static final int TOTAL_BG_MAP = 1;
	public static final int BG_M1 = 0;
	
	// map 1 backgrounds
	public static final int M1_IMAGE1 = 0;
	
	
	// types for loading sprite
	protected static final int TYPE_PLAYER_SPRITES = 0;
	protected static final int TYPE_TILES = 1;
	protected static final int TYPE_ENEMIES = 2;
	protected static final int TYPE_HUD = 3;
	protected static final int TYPE_ITEMS = 4;
	
	// enemies
	public static final int NUM_OF_ENEMIES = 5;
	public static final int NUM_OF_ENEMIES_FRAMES = 2;
	public static final int NUM_OF_ROW_ENEMIES = 2;
	public static final int NUM_OF_COL_ENEMIES = 5;
	public static final int ENEMY_BOX_WIDTH = 80;
	public static final int ENEMY_BOX_HEIGHT = 70;
	public static final int [] ENEMIES_WIDTH = {51, 51, 66, 62, 72, 75, 50, 51, 54, 57};
	public static final int [] ENEMIES_HEIGHT = {51, 51, 42, 43, 36, 31, 28, 28, 31, 31};
	public static final int BLOCKER = 0;
	public static final int FISH = 1;
	public static final int FLY = 2;
	public static final int SLIME = 3;
	public static final int SNAIL = 4;
	
	// liquid
	public static final int WATER = 0;
	public static final int LAVA = 1;
	
	
	// HUD
	public static final int HUD_TOTAL = 8;
	public static final int [] HUD_WIDTH = {47, 53, 53, 53, 44, 47, 47, 47};
	public static final int [] HUD_HEIGHT = {47, 45, 45, 45, 40, 47, 47, 47};
	public static final int HUD_BOX_WIDTH = 70;
	public static final int HUD_BOX_HEIGHT = 70;
	
	// items
	public static final int ITEMS_TOTAL = 5;
	public static final int [] ITEMS_WIDTH = {128, 129, 129, 36, 60};
	public static final int [] ITEMS_HEIGHT = {71, 71, 71, 36, 36};
	public static final int ITEMS_BOX_WIDTH = 130;
	public static final int ITEMS_BOX_HEIGHT = 75;
	public static final int COIN = 0;
	public static final int KEY = 1;
}