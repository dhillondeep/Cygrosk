package com.deep.game.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.deep.game.entity.Door;
import com.deep.game.entity.Enemy;
import com.deep.game.entity.HUD;
import com.deep.game.entity.Item;
import com.deep.game.entity.Liquid;
import com.deep.game.entity.Player;
import com.deep.game.entity.PlayerAttributes;
import com.deep.game.entity.enemies.Blocker;
import com.deep.game.entity.enemies.Fish;
import com.deep.game.entity.enemies.Fly;
import com.deep.game.entity.enemies.Slime;
import com.deep.game.entity.enemies.Snail;
import com.deep.game.entity.items.Coin;
import com.deep.game.entity.items.Key;
import com.deep.game.entity.liquids.Lava;
import com.deep.game.entity.liquids.Water;
import com.deep.game.handlers.Keyboard;
import com.deep.game.resources.Constants;
import com.deep.game.resources.Resources;
import com.deep.game.tileMap.Clouds;
import com.deep.game.tileMap.TileMap;
import com.deep.game.tileMap.View;

// this class controls the level 1 of the game
public class M1Level1State extends GameState
{
	// fields 

	private TileMap tileMap;					// TileMap object
	private Player player;						// Player object
	private ArrayList<Enemy> enemies;			// Array list of Enemy objects
	private ArrayList<Liquid> liquids;			// Array list of Liquid objects
	private ArrayList<Item> items;				// Array list of Item objects
	private View view;							// View object
	private Clouds clouds;						// Clouds object
	private HUD hud;							// HUD object
	private Door door;							// Door object

	// states for the this main state
	private int stateProgress;					// progress of certain state
	private boolean startState;					// boolean to check if startState should be played
	private boolean deadState;					// boolean to check if deadState should be played
	private boolean finishState;				// boolean to check if finishState should be played
	private ArrayList<Rectangle> fade;			// array list of rectangles to show fades
	private boolean noControl;					// boolean to restrict keyboard controls
	private boolean screenSwitch;				// boolean to show the main game screen

	// pause menu
	private BufferedImage pauseImage;			// image for the pause screen
	private boolean shouldPause;				// boolean to decide if game should pause or not
	private boolean isPaused;					// boolean to check if the game is pasued or not
	private long pauseWaitTime;					// wait time between pausing
	private long pauseStartTime;				// start time when game is paused

	// constructor initializes everything
	public M1Level1State(GameStateManager gsm)
	{
		// call parent constructor
		super(gsm);

		// initialize everything
		initialize();
	} // end constructor

	// initialize method initializes everything
	public void initialize()
	{
		// initialize tileMap
		tileMap = new TileMap(70, 0, 0, gsm.gamePanel);

		// load tiles
		tileMap.loadTiles();

		// load map 1
		tileMap.loadMap(Constants.LOC_MAP1);

		// initialize fade array list
		this.fade = new ArrayList<>();
		this.startState = true;			// show startState first
		this.noControl = true;			// block controls
		this.screenSwitch = true;		// show main game screen

		// load pause image
		pauseImage = Resources.getPauseImg();

		// make should pause true
		shouldPause = true;

		// make is paused false
		isPaused = false;

		// set wait time to 500 ms
		pauseWaitTime = 500;

		// set up the map
		setUpMap();
	} // end initialize method

	// setUpMap method sets up the tile map
	private void setUpMap()
	{
		// initialize player object
		player = new Player(tileMap, gsm.gamePanel, PlayerAttributes.getPlayerNum());

		// set player position
		this.player.setPosition(150.0, 560.0);

		// set player attributes based on attributes saved in PlayerAttributes class
		this.player.setLives(PlayerAttributes.getLives());
		this.player.setHealth(PlayerAttributes.getHealth());
		this.player.setCoins(PlayerAttributes.getCoins());
		this.player.setTime(PlayerAttributes.getTime());

		// initialize door object
		this.door = new Door(tileMap, player, 3500, 240, 70, 110);

		// populate enemies
		populateEnemies();

		// add liquids
		addLiquids();

		// add backgrounds
		addBackgrounds();

		// add items
		addItems();

		// initialize HUD
		hud = new HUD(player);
	} // end setUpMap method

	// add Items method add coins and keys on the map
	private void addItems()
	{
		// initialize items array
		items = new ArrayList<>();

		// get images from resources
		BufferedImage[] itemsImgs = { Resources.getItems()[3], Resources.getItems()[4] };

		// itemsNum array holds item type
		int[] itemsNum = {
				Constants.COIN, Constants.COIN, Constants.COIN, Constants.COIN, 
				Constants.COIN, Constants.COIN, Constants.KEY, Constants.COIN,
				Constants.COIN, Constants.COIN};

		// location array holds location for each item type
		Point[] locaion = { new Point(700, 250), 
				new Point(1190, 140), 
				new Point(1200, 590), 
				new Point(1400, 590), 
				new Point(1470, 590), 
				new Point(1540, 590), 
				new Point(1590, 170), 
				new Point(2170, 280), 
				new Point(2940, 280), 
				new Point(3010, 280) };

		// go through all the items
		for (int count = 0; count < itemsNum.length; count++)
		{
			// Items object
			Item i = null;

			// switch statement
			switch (itemsNum[count])
			{
			// if coin is the item type
			case Constants.COIN: 
				// create a coin object
				i = new Coin(tileMap, gsm.gamePanel, itemsImgs[Constants.COIN]);
				// set the position of the object
				i.setPosition(locaion[count]);
				break;
				// if key is the item type 
			case Constants.KEY: 
				// create a key object
				i = new Key(tileMap, gsm.gamePanel, itemsImgs[Constants.KEY]);
				// set the position of the object
				i.setPosition(locaion[count]);
				break;
			} // end switch statement

			// add the item to the array list
			items.add(i);
		} // end for loop
	} // end addItems method

	// populateEnemies method creates enemies and add them to the array list
	private void populateEnemies()
	{
		// initialize enemies array
		enemies = new ArrayList<>();

		// array to store type of the enemy
		int[] enemiesNum = { Constants.FISH, Constants.SLIME, Constants.FLY, Constants.SNAIL, 
				Constants.BLOCKER, Constants.SLIME, Constants.SLIME, Constants.FISH };

		// array to store the location of the enemies
		Point[] locationPoints = { new Point(620, 550), 
				new Point(690, 322), 
				new Point(1700, 140), 
				new Point(2110, 319), 
				new Point(1280, 560), 
				new Point(2930, 322), 
				new Point(3000, 322), 
				new Point(3120, 630) };

		// array to store the minimum x value for enemies
		int[] min = { -1, 630, -1, 2030, -1, 2870, 2870, -1 };
		// array to store the maximum x value for enemies
		int[] max = { -1, 910, 1750, 2240, -1, 3080, 3080, 3560 };

		// size of the blocker
		int[] bSize = { 1 };

		// counter for the blocker
		int bCounter = 0;

		// for loop to go through all enemy types
		for (int count = 0; count < enemiesNum.length; count++)
		{
			// create Enemy object
			Enemy e = null;

			// switch states based on enemy type
			switch (enemiesNum[count])
			{
			// if blocker is the enemy type
			case Constants.BLOCKER: 
				// create a blocker
				e = new Blocker(tileMap, gsm.gamePanel, bSize[bCounter], min[count], max[count]);
				// set position
				e.setPosition(locationPoints[count]);
				// add one to the blocker counter
				bCounter++;
				break;

				// if fish is the enemy type
			case Constants.FISH: 
				// create a fish
				e = new Fish(tileMap, gsm.gamePanel, min[count], max[count]);
				// set position
				e.setPosition(locationPoints[count]);
				break;

				// if fly is the enemy type
			case Constants.FLY: 
				// create a fly
				e = new Fly(tileMap, gsm.gamePanel, min[count], max[count]);
				// set position
				e.setPosition(locationPoints[count]);
				break;

				// if slime is the enemy type
			case Constants.SLIME: 
				// create a slime
				e = new Slime(tileMap, gsm.gamePanel, min[count], max[count]);
				// set position
				e.setPosition(locationPoints[count]);
				break;
				//if snail is the enemy type
			case Constants.SNAIL: 
				// create a snail
				e = new Snail(tileMap, gsm.gamePanel, min[count], max[count]);
				// set position
				e.setPosition(locationPoints[count]);
			} // end switch statement

			// add enemy to the enemy array list
			this.enemies.add(e);
		} // end for loop
	} // end populateEnemies method

	// addLiquids method add liquids to the map
	private void addLiquids()
	{
		// initialize liquids array list
		liquids = new ArrayList<>();

		// liquid types
		int[] liquidsNum = {Constants.WATER, Constants.LAVA, Constants.WATER};

		// location for each liquid type
		Point[] location = { new Point(560, 490), 
				new Point(1681, 630), 
				new Point(3080, 560) };

		// rows for each liquid type
		int[] rows = { 3, 1, 2 };
		// columns for each liquid type
		int[] cols = { 8, 17, 7 };

		// for loop to go through all liquids
		for (int count = 0; count < liquidsNum.length; count++)
		{
			// create liquid object
			Liquid l = null;

			// switch states based on liquid type
			switch (liquidsNum[count])
			{
			// if liquid type is water
			case Constants.WATER: 
				// create water object
				l = new Water(tileMap, location[count], rows[count], cols[count]);
				break;

				// if liquid type is lava
			case Constants.LAVA: 
				// create lava object
				l = new Lava(tileMap, location[count], rows[count], cols[count]);
				break;
			}
			// add liquid to liquid array list
			this.liquids.add(l);
		} // end for loop
	} // end addLiquids method

	// addBackgrounds method add backgrounds
	private void addBackgrounds()
	{
		// get view image from resources
		BufferedImage view_image = Resources.getBackgroundImg();

		// initialize view object
		this.view = new View(view_image, 0, 0);

		// initialize clouds object
		this.clouds = new Clouds(0, 0, 0.2, gsm.gamePanel);
	} // end addBackgrounds method

	// draw methods draws images on screen
	public void draw(Graphics g)
	{
		// only draw main game screen of screenSwitch is false
		if (!screenSwitch)
		{
			// draw view
			view.draw(g);

			// draw clouds
			clouds.draw(g);

			// draw tile map
			tileMap.draw(g);

			// draw door
			door.draw(g);

			// draw player
			player.draw(g);

			// go through all liquids and draw them
			for (int count = 0; count < liquids.size(); count++) 
				liquids.get(count).draw(g);

			// go through all items and draw them
			for (int count = 0; count < items.size(); count++) 
				items.get(count).draw(g);

			// go through all enemies and draw them
			for (int count = 0; count < enemies.size(); count++) 
				enemies.get(count).draw(g);

			// draw HUD
			hud.draw(g);
		} // end if statement

		// if there is startState, deadState or finishState
		if (startState || deadState || finishState)
		{
			// set the color to black
			g.setColor(Color.BLACK);

			System.out.println("Draw");

			// go through fade array list
			for (int count = 0; count < fade.size(); count++)
			{
				// store rectangle
				Rectangle r = fade.get(count);

				// fill a rectangle using the bounds from the rectangle object
				g.fillRect((int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight());
			} // end for loop
		} // end if statement

		// draw pause screen
		if (isPaused)
			g.drawImage(pauseImage, 0, 0, null);
	} // end draw method

	// update method update everything on the screen
	public void update()
	{
		// if stratState is true, go to startState
		if (startState)
			startState();
		// if deaadState is true, go to deadState
		else if (deadState) 
			deadState();
		// if finishState is true, go to finishState
		else if (finishState)
			finishState();
		
		// if player is dead, reset pause
		if (player.getHealth() == 0) 
			isPaused = false;

		// handle inputs
		inputHandler();

		// update everything if game is not paused
		if (!isPaused)
		{
			// set the x position of the clouds based on how fast tileMap moves
			clouds.setPosition(this.tileMap.getX(), 0.0);

			// update the player
			player.update();

			// update the door
			door.update();

			// set the tileMap x position based on how player moves
			// player will always be in center except at beginning and at end
			tileMap.setPosition(gsm.gamePanel.getWidth() / 2 - player.getX(), tileMap.getY());

			// go through all enemies and update them
			for (int count = 0; count < this.enemies.size(); count++)
				enemies.get(count).update();

			// check if there is any map collision between player and other enemies, liquids
			checkMapObjectCollisions();

			// if player's health is 0 and shouldDie is true
			if (player.getHealth() == 0 && player.shouldDie())
			{
				deadState = true;		// make deadState true
				noControl = true;		// make noControl true to restrict any player movement
				player.setDead();		// set player dead
			} // end if statement
		}
	} // end update method

	// checkMapObjectCollisions method checks collisions among map objects
	private void checkMapObjectCollisions()
	{
		// check player collision with all enemies
		player.checkEnemyCollisons(enemies);

		// check player collision with liquids
		player.checkLiquidCollisions(liquids);

		// go through all items and check player collision with it
		for (int count = 0; count < this.items.size(); count++) 
		{
			// check if an item and player are intersecting
			if (items.get(count).isIntersecting(this.player))
			{
				// if true, check types

				// if type is a coin, add coin to the player
				if (this.items.get(count).getType() == Constants.COIN) 
					player.addCoin();
				// if type is a key, add key to the player
				else 
					player.addKey();

				// remove the item from the items array list
				items.remove(count);
			} // end if statement
		} // end for loop

		// if door is open
		if (door.getDoorStatus() == Door.OPEN)
		{
			finishState = true;			// make finish state true
			noControl = true;			// restrict any player movement
			door.setGameFinished(true);	// set game finished true
		} // end if statement
	} // end checkMapObjectCollisions method

	// startState method handles the state when level boots up
	private void startState()
	{
		////////// this will run 60 time per second ////////////

		// increment stateProgress 
		stateProgress += 1;

		// this is done first time
		if (stateProgress == 1)
		{
			// stop pausing
			isPaused = false;

			// clear the fade
			fade.clear();

			// make screenSwitch false so that main screen cannot be shown
			screenSwitch = false;

			// vertically half size rectangle and it is at the top
			fade.add(new Rectangle(0, 0, gsm.gamePanel.getWidth(), gsm.gamePanel.getHeight() / 2));

			// horizontally half size rectangle and it is on the left side
			fade.add(new Rectangle(0, 0, gsm.gamePanel.getWidth() / 2, gsm.gamePanel.getHeight()));

			// vertically half size rectangle and it is at the bottom
			fade.add(new Rectangle(0, gsm.gamePanel.getHeight() / 2, 
					gsm.gamePanel.getWidth(), gsm.gamePanel.getHeight() / 2));

			// horizontally half size rectangle and it is on the right side
			fade.add(new Rectangle(gsm.gamePanel.getWidth() / 2, 0, 
					gsm.gamePanel.getWidth() / 2, gsm.gamePanel.getHeight()));
		} // end if 

		// run this until progress is 60
		if (stateProgress > 1 && stateProgress < 60)
		{
			// reduce width and height and increase x and y
			// this shows fade
			fade.get(0).height -= 6;
			fade.get(1).width -= 11;
			fade.get(2).y += 6;
			fade.get(3).x += 11;
		} // end if statement

		// run this one last time
		if (stateProgress >= 60)
		{
			startState = false;			// make startState false
			stateProgress = 0;			// set state progress to 0
			fade.clear();				// clear the fade
			noControl = false;			// allow controls
			shouldPause = true;			// allow pausing
		} // end if statement
	} // end startState method

	private void deadState()
	{
		//////////this will run 60 time per second ////////////

		// increment stateProgress
		stateProgress += 1;

		// run first time
		if (stateProgress == 1) 
		{
			// stop pausing
			isPaused = false;

			// since player is dead, make it lose life
			player.loseLife();
		} // end if statement

		// wait for few increment until its 60
		if (this.stateProgress == 60)
		{
			fade.clear(); // clear the fade
			// create a rectangle in center
			fade.add(new Rectangle(gsm.gamePanel.getWidth() / 2, gsm.gamePanel.getHeight() / 2, 0, 0));
		} // end if statement

		// if progress is greater than 60
		if (stateProgress > 60)
		{
			// decrease the x and y value and increase the width and height
			// this will look like rectangle getting bigger
			fade.get(0).x -= 11;
			fade.get(0).y -= 6;
			fade.get(0).width += 22;
			fade.get(0).height += 12;
		} // end if statement

		// if progress is greater than or equal to 140
		if (stateProgress >= 140) 
		{
			// if player is out of lives, go to game over state
			if (player.getLives() <= 0)
				this.gsm.setState(GameStateManager.GAMEOVER_STATE);
			// if player still has lives
			else
			{
				deadState = false;			// make dead state false
				startState = true;			// make start state true
				stateProgress = 0;			// make progress 0
				player.reset();				// reset the player
				fade.clear();				// clear the fade
				screenSwitch = true;		// make screen switch true since screen is switching
			} // end if statement
		} // end if statement
	} // end deadState method

	private void finishState()
	{
		//////////this will run 60 time per second ////////////

		// increment the state progress by 1
		stateProgress += 1;

		// if progress is 60
		if (stateProgress == 60)
		{
			// stop pausing
			isPaused = false;

			// clear the fade
			fade.clear();

			// make a rectangle in center
			fade.add(new Rectangle(gsm.gamePanel.getWidth() / 2, gsm.gamePanel.getHeight() / 2, 0, 0));
		} // end if statement

		// if progress is greater than 60
		if (this.stateProgress > 60)
		{
			// decrease the x and y value and increase width and height
			// this will look like rectangle is getting bigger
			fade.get(0).x -= 11;
			fade.get(0).y -= 6;
			fade.get(0).width += 22;
			fade.get(0).height += 12;
		} // end if statement

		// if progress is greater than or equal to 140
		if (stateProgress >= 140)
		{
			finishState = false;
			player.stopEverything();

			// set player attributes in PlayerAttributes class
			PlayerAttributes.setLives(this.player.getLives());
			PlayerAttributes.setHealth(this.player.getHealth());
			PlayerAttributes.setCoins(this.player.getCoins());
			PlayerAttributes.setTime(this.player.getTime());
			PlayerAttributes.setPlayerNum(this.player.getPlayerNum());
			PlayerAttributes.setScore(this.player.getScore());

			// set the game state to game over state
			gsm.setState(GameStateManager.GAMEOVER_STATE);
		} // end if statement
	} // end finishState method

	// inputHandler handles inputs
	public void inputHandler()
	{
		// only run controls if noControl is false
		if (!noControl)
		{
			////////////////////////////////////////////////////////////

			// if a or left key is pressed, make player go left
			if (Keyboard.isKeyPressed(Keyboard.LEFT) || Keyboard.isKeyPressed(Keyboard.A)) 
			{
				this.player.setGoingLeft(true);
			}
			// if d or right key is pressed, make player go right
			if (Keyboard.isKeyPressed(Keyboard.RIGHT) || Keyboard.isKeyPressed(Keyboard.D)) 
			{
				this.player.setGoingRight(true);
			}
			// if w or space bar key is pressed, make player jump
			if (Keyboard.isKeyPressed(Keyboard.SPACEBAR) || Keyboard.isKeyPressed(Keyboard.W)) 
			{
				this.player.setJumping(true);
			}
			// if s or down key is pressed, make player duck
			if (Keyboard.isKeyPressed(Keyboard.DOWN) || Keyboard.isKeyPressed(Keyboard.S)) 
			{
				this.player.setDucking(true);
			}

			/////////////////////////////////////////////////////////////
			// release all the keys and set everything to false if those keys are not pressed

			if (!Keyboard.isKeyPressed(Keyboard.LEFT) && !Keyboard.isKeyPressed(Keyboard.A)) 
			{
				this.player.setGoingLeft(false);
			}
			if (!Keyboard.isKeyPressed(Keyboard.RIGHT) && !Keyboard.isKeyPressed(Keyboard.D)) 
			{
				this.player.setGoingRight(false);
			}
			if (!Keyboard.isKeyPressed(Keyboard.SPACEBAR) && !Keyboard.isKeyPressed(Keyboard.W)) 
			{
				this.player.setJumping(false);
			}
			if (!Keyboard.isKeyPressed(Keyboard.DOWN) && !Keyboard.isKeyPressed(Keyboard.S)) 
			{
				this.player.setDucking(false);
			}
		} // end if statement

		// find elapsed time
		long elapsed = System.currentTimeMillis() - pauseStartTime;

		// if there is elapsed time, allow pausing
		if (elapsed >= pauseWaitTime)
			shouldPause = true;

		// if allowed to pause and use clicks 'ESC' key
		if (shouldPause && Keyboard.isKeyPressed(Keyboard.ESC))
		{
			shouldPause = false;							// make should pause false
			isPaused = !isPaused;							// make isPaused inverse of what it was
			pauseStartTime = System.currentTimeMillis();	// reset the start time
		} // end if statement
	} // end inputHandler method
} // end M1Level1State class
