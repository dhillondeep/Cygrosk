package game.entity;

import game.core.GamePanel;
import game.resources.Resources;
import game.tileMap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

// this class controls the player behavior on the tile map
public class Player extends MapObject
{
	// player attributes related
	private final int PLAYER_NUM;						// player number
	private final int MAX_HEALTH;						// maximum health of the player
	private final int MAX_LIVES;						// maximum lives of the player
	private final int MAX_FLINCH_TIME;					// maximum times the player will flinch
	private final int MAX_HURT_TIME;

	// animation constants
	private static final int TOTAL_ANIMATIOMS = 5;		// number of animations 
	private static final int WALKING = 0;				// walking
	private static final int DUCKING = 1;				// ducking
	private static final int HURT = 2;					// hurt
	private static final int JUMPING = 3;				// jumping
	private static final int STANDING = 4;				// standing


	// player attributes
	private int lives;									// current number of lives of the player
	private int health;									// current health of the player
	private boolean isFlinching;						// boolean to check if the player is flinching or not
	private long flinchTime;							
	private boolean isHurt;
	private long hurtTimer;
	private int keys;									// stores the score of the player
	private int coins;
	private long time;									// time passed while player is playing
	private boolean isDucking;							// boolean to keep track if player is ducking or not
	private boolean isDead;
	private boolean isHurtRight;
	private boolean shouldDie;
	private int score;

	// animation related
	private long jumpTimeStart;							// variable to store the time when player will start jumping
	private final long jumpTimeAllowed;					// constant to store the maximum jump time
	private boolean isAdjustable;						// boolean to keep track if y value of the player is adjustable or not
	private int adjCount;								// variable to keep track of how many times y value is adjusted
	private boolean showJumpAnimation;					// boolean to keep track of when to show jumping animation
	private BufferedImage [][] sprites;					// 2D array to store various sprites of the player
	
	// constructor creates the player
	public Player(TileMap tm, GamePanel panel, int playerNum) 
	{
		// call the super constructor to initialize fields and global variables
		super(tm, panel);

		this.PLAYER_NUM = playerNum;	// player number
		this.MAX_HEALTH = 4;;			// maximum health for the player
		this.MAX_LIVES = 3;				// maximum lives for the player
		this.MAX_FLINCH_TIME = 1500;
		this.MAX_HURT_TIME = 180;

		width = 50;				// width of the player
		height = 70;			// height of the player

		moveSpeed = 0.6;		// move speed of the player
		maxSpeed = 4.0;			// maximum move speed of the player
		stopSpeed = 0.50;		// stop speed of the player
		fallSpeed = 0.64;		// fall speed of the player
		maxFallSpeed  = 12;		// maximum fall speed of the player
		jumpSpeed = -15.0;		// jump speed of the player
		stopJumpSpeed = 0.30;	// stop jump speed of the player
		jumpTimeAllowed = 450;	// jump time allowed for the player

		adjCount = 1;

		// initialize sprites array
		sprites = new BufferedImage[TOTAL_ANIMATIOMS][];


		// add sprites
		sprites[WALKING] = Resources.getWalkingImgs(PLAYER_NUM);
		sprites[STANDING] = Resources.getStandingImgs(PLAYER_NUM);
		sprites[DUCKING] = Resources.getDuckingImgs(PLAYER_NUM);
		sprites[JUMPING] = Resources.getJumpingImgs(PLAYER_NUM);
		sprites[HURT] = Resources.getHurtImgs(PLAYER_NUM);

		setLives(MAX_LIVES);
		setHealth(MAX_HEALTH);

		isFacingRight = true;
		setAnimation(STANDING, -1, false);
		score = 0;
	} // end constructor

	// draw method draws the player on screen
	public void draw(Graphics g)
	{
		// if player is flinching
		if (isFlinching)
		{
			// find the time elapsed
			long elapsed = getMillis(System.nanoTime() - flinchTime) / 100;

			// every third elapsed time, do not draw anything
			if (elapsed % 3 == 0)
				return;

			// if player is hurt. 
			if (isHurt)
			{
				// if player is hurt to the right, draw image with right direction
				if (isHurtRight)
				{
					g.drawImage(
							animation.getImage(),
							(int) (getLX() + xMap), 
							(int) (getLY() + yMap),
							null
							);
				}
				// if player is hurt to the left, draw image with left direction
				else if (!isHurtRight)
				{
					g.drawImage(
							animation.getImage(),
							(int) (getLX() + xMap + width), 
							(int) (getLY() + yMap),
							-width,
							height,
							null
							);
				} // end if

				// return when done
				return;
			} // end if statement
		} // end if statement
		
		// if player is not flinching, just draw a regular image
		super.draw(g);
	} // end draw method

	// checkEnemyCollisions method checks player collision with enemies
	public void checkEnemyCollisons(ArrayList<Enemy> enemies) 
	{
		// go through all the enemies
		for (int count = 0; count < enemies.size(); count++) 
		{
			// store enemy object
			Enemy e = enemies.get(count);

			// if enemy and player intersect, hit the player
			if (isIntersecting(e))
				hit();
		} // end for loop
	} // end checkEnemyCollisions method
	
	// checkLiquidCollisions method checks player collision with liquids
	public void checkLiquidCollisions(ArrayList<Liquid> liquids) 
	{
		// go through all the liquids
		for (int count = 0; count < liquids.size(); count++) 
		{
			// store liquid object
			Liquid l = liquids.get(count);

			// if liquid contains player
			if (l.contains(this))
			{
				setHealth(0);			// set player's health to 0
				shouldDie = true;		// make shouldDie true so that player can die
			} // end if statement
		} // end for loop
	} // end checkLiquidCollisions methos

	// reset method resets the player
	public void reset() 
	{
		// set the health to maximum health
		setHealth(MAX_HEALTH);
		
		// set the position to the default position
		setPosition(150, 560);
		
		isFacingRight = true;		// make player face right
		shouldDie = false;			// make player shouldDie false
		isFlinching = false;		// make player isFlinching false
		isHurt = false;				// make player isHurt false
		stopEverything();			// stop everything
		setAnimation(STANDING, -1, false);	// set the animation to standing
	} // end reset method

	// stopEverything stops the player from doing anything
	public void stopEverything() 
	{
		// set everything false
		isGoingLeft = false;
		isGoingRight = false;
		isGoingUp = false;
		isDucking = false;
		isGoingDown = false;
		isFalling = false;
		isJumping = false;
		isFlinching = false;
		isHurt = false;
		dx = 0;
		dy = 0;
	} // end stopEverything method

	// hit method changes player when it is hit
	private void hit()
	{
		// if player is flinching, return since player cannot be harmed when it is flinching
		if (isFlinching) return;
		
		// lose health
		loseHealth();

		// if health is less than 0, set the health back to 0
		if (getHealth() < 0) setHealth(0);

		isFlinching = true;			// set isFlinching to true
		isHurt = true;				// set isHurt to true
		
		// set the flinchTime and hurtTimer equal to the current time
		flinchTime = hurtTimer = System.nanoTime();

		// if player is facing right, set isHurtRight true
		if (isFacingRight)
			isHurtRight = true;
		// if player is facing left, set isHurtRight false
		else
			isHurtRight = false;
	} // end hit method

	// update method updates the player
	public void update()
	{	
		// add one to the time
		++time;
		
		// check if player is hurt and based on that modify player attributes
		hurtMode();

		// set the next position for the player
		setPosition();

		// check collision
		checkTileMapCollision();

		// set actual player x and y position
		setPosition(xTemporary, yTemporary);

		// adjust y position based on what animation it is
		// this is because in ducking animation player has to be drawn little more downwards than other animations
		// for now, change y value by 22 pixels
		adjustPosition(22);

		// update animations
		updateAnimations();

		// update the direction in which the player is facing
		updateFacingDirection();

	} // end update method

	// hurtMode method checks if player is hurt and based on that modifies player attributes
	private void hurtMode() 
	{
		// if the player is hurt
		if (isHurt)
		{
			// find the elapsed time
			long elapsed = getMillis(System.nanoTime() - hurtTimer);

			// if maximum elapsed time has reached, set isHurt false
			if (elapsed > MAX_HURT_TIME)
			{
				isHurt = false;

				dx = 0; 	// stop the player
				
				isFacingRight = isHurtRight; // set player's direction based on hurt direction
			}
			// if maximum elapsed time has not reached yet
			else
			{
				// if player is facing right, make the player move back
				if(isHurtRight) 
					dx = -1;
				// if player is facing left, make the player move forward
				else if (!isHurtRight)
					dx = 1;

				// make the player jump up a little
				dy = -3;

				// set the animation to hurt
				setAnimation(HURT, -1, true);

				// update the animation
				animation.update();
			} // end if statement
		} // end if statement

		// if player is flinching
		if (isFlinching)
		{
			// find the elapsed time in milliseconds
			long elapsed = getMillis(System.nanoTime() - flinchTime);

			// if half of the flinching is done and player's health is 0, make shouldDie true
			if (elapsed > (MAX_FLINCH_TIME / 2) && getHealth() == 0)
				shouldDie = true;

			// if making flinch time is achieved, make isFlinching false
			if (elapsed > MAX_FLINCH_TIME)
				isFlinching = false;
		} // end if statement
	} // end hurtMode method

	// setPosition method sets the position of the player
	private void setPosition() 
	{
		// do not set player's anything if he/she is hurt
		if (isHurt) return;

		// if player is going left
		if (isGoingLeft)
		{
			// make the dx a negative number so that player goes to left
			dx -= moveSpeed;

			// if dx has reached the maximum left side speed, stop increasing (actually its decreasing) the dx value
			if (dx < -maxSpeed)
				dx = -maxSpeed;
		} // end if statement

		// if player is going right
		else if (isGoingRight)
		{
			// make the dx a positive number so that player goes to right
			dx += moveSpeed;

			// if dx has reached the maximum right side speed, stop increasing the dx value
			if (dx >= maxSpeed)
				dx = maxSpeed;
		} // end if statement

		// if player is not going left or right
		else
		{
			// if player still has dx in right direction
			if (dx > 0)
			{
				// subtract the stopSpeed from the player so that player can stop
				// this is like friction to the player
				dx -= stopSpeed;

				// if dx became lower than 0, make it 0 so that player can stop
				if (dx < 0)
					dx = 0;
			} // end if statement
			// if player still has dx in left direction
			else if (dx < 0)
			{
				// add the stopSpeed to the player so that player can stop
				dx += stopSpeed;

				// if dx became greater than 0, make it 0 so that player can stop
				if (dx > 0)
					dx = 0;
			} // end if statement
		} // end if statement

		// if a player is ducking, it cannot jump, fall or go left or right
		if (isDucking)
		{
			isJumping = false;		// set jumping to false
			isFalling = false;		// set falling to false
			isGoingLeft = false;	// set going left to false
			isGoingRight = false;	// set going right to false
			isAdjustable = true; 	// allow adjusting player position

			dx = 0;					// set dx to 0
			dy = 0;					// set dy to 0
		}
		// if player is not ducking, do not allow adjusting player position or use original position
		else
			isAdjustable = false;

		// if player is jumping
		if (isJumping)
		{
			// start the timer so that jumping animation can be shown
			jumpTimeStart = System.nanoTime();
			showJumpAnimation = true;	// make showJumpAnimation true so that jump animation can be shown
			dy = jumpSpeed; // set the jumping speed
			isFalling = true; // set the player so that it starts to fall
			isJumping = false; // set the jumping is equal to false so that player cannot double jump
		} // end if statement

		// if player is falling
		if (isFalling)
		{
			// start adding fallSpeed to the dy so that player slowly starts coming down
			dy += fallSpeed;

			// if player has reached the terminal velocity, set the dy to maxFallSpeed
			if (dy > maxFallSpeed) dy = maxFallSpeed;
		} // end if statement

		// check if the player is going out of screen on the left side or the right side
		if ((getLX() + xMap + dx) <= 0 || (getLX() + xMap + dx + width) >= 1260)
			// if player is going out of screen, set its dx to 0 so that it can stop
			dx = 0;

		// stop jump animation if player hits something upwards
		if (isTileCollisionUp())
			// set showJumpAnimation to false
			showJumpAnimation = false;
	} // end setPosition method

	// getTimeToString method converts the time in proper format and return it as a string
	public String getTimeToString() 
	{
		// find minutes by dividing time by 3600
		int minutes = (int) (time / 3600);
		// find seconds by finding the remainder after dividing by 3600 and then dividing by 60
		int seconds = (int) ((time % 3600) / 60);
		
		// if there is no 2 digit number in seconds, add 0 otherwise do not add 0
		return seconds < 10 ? minutes + ":0" + seconds : minutes + ":" + seconds;
	} // end getTimeToString method

	// setDucking method sets ducking true or false
	public void setDucking(boolean b) 
	{
		// only set ducking if not falling
		if (!isFalling)
		{
			// do not set ducking true unless its false before 
			if (b == true && !isDucking)
			{
				// set ducking equal to the boolean
				isDucking = b;

				// reset the adjCounter
				adjCount = 0;
			}
			// set the ducking false when its instructed to do so
			else
				isDucking = b;
		} // end if statement
	} // end setDucking method

	// setAnimation method sets the animation based on the currenyAnimation variable
	public void setAnimation(int type, int delay, boolean check)
	{
		// if check is enabled, check
		if (check)
		{
			// check if the new animation is not equal to the current animation
			if (currAnimation != type)
			{
				// if true, set current animation equal to type
				currAnimation = type;
				// set frames for the animation
				animation.setFrames(sprites[type]);
				// set delay based on the delay provided
				animation.setDelay(delay);
			}
		}
		// if check is disabled, do not check
		else
		{
			// set current animation equal to type
			currAnimation = type;
			// set frames for the animation
			animation.setFrames(sprites[type]);
			// set delay based on the delay provided
			animation.setDelay(delay);
		} // end if statement
	} // end setAnimation method

	// setJumping method sets jumping true or false
	public void setJumping(boolean b) 
	{
		// only set jumping if not falling
		if (!isFalling)
			this.isJumping = b; 
	} // end setJumping method

	// updateFacingDirection method updates the direction of the player
	public void updateFacingDirection()
	{
		// if player is moving left, set isFacingRight false
		if (dx < 0)
			isFacingRight = false;
		// if player is moving right, set iFacingRight true
		else if (dx > 0)
			isFacingRight = true;
	} // end updateFacingDirection method

	// updateAnimations method updates the animations
	public void updateAnimations()
	{
		if (isHurt) return;

		// -1 delay means there is no delay

		// set animation to jumping if time is not passed and showJumpingAnimation is true
		if (((System.nanoTime() - jumpTimeStart) / 1000000 <= jumpTimeAllowed) && showJumpAnimation)
			setAnimation(JUMPING, -1, true);

		// if player is going left and right and it is not falling, set animation to walking
		else if ((isGoingLeft || isGoingRight) && !isFalling)
			setAnimation(WALKING, 40, true);
		// if player is ducking, set animation to ducking
		else if (isDucking)
			setAnimation(DUCKING, -1, true);
		// if player is doing nothing, set animation to standing
		else
			setAnimation(STANDING, -1, true);

		// update the animation
		animation.update();
	} // end updateAnimations method

	// adjustPosition method adjusts the y value of the player
	public void adjustPosition(int adjValue)
	{
		// if isAdjustable is true and adjCount is 0, adjust value
		// this allows only 1 adjustment in one animation
		if (isAdjustable && adjCount == 0)
		{
			y += adjValue;	// add adjValue to the y
			// increase the adjCount
			++adjCount;
		}
		// if !isAdjustable is true and adjCount is 0, adjust value
		// this allows only 1 adjustment in one animation
		else if (!isAdjustable && adjCount == 1)
		{
			y -= adjValue;

			// increment the adjCount
			++adjCount;
		} // end if statement
	} // end adjustPosition method
	
	// isDead method returns true or false based on if player is dead
	public boolean isDead()
	{
		return isDead;
	} // end isDead method

	// setDead method sets player dead
	public void setDead() 
	{
		isDead = true;
		
		// stop everything when player is dead
		stopEverything();
	} // end setDead method

	// isFlinching method returns true or false based on if player is flinching
	public boolean isFlinching() 
	{
		return isFlinching;
	} // end isFlinching method

	// isHurt method returns true or false based on if player is hurt
	public boolean isHurt() 
	{
		return isHurt;
	} // end isHurt method

	// shouldDie method returns true or false based on if shouldDie boolean is true or false
	public boolean shouldDie() 
	{
		return shouldDie;
	} // end shouldDie method

	// getPlayerNum method returns player number
	public int getPlayerNum() 
	{
		return PLAYER_NUM;
	} // end getPlayerNum method

	// getKeys method returns number of keys
	public int getKeys() 
	{
		return keys;
	} // end getKeys method

	// getCoins method returns number of coins
	public int getCoins() 
	{
		return coins;
	} // end getCoins method
	
	// addCoin method adds one coin to the total
	public void addCoin()
	{
		++coins;
	} // end addCoin method
	
	// addKey method adds one key to the total
	public void addKey()
	{
		++keys;
	} // end addKey method
	
	// getScore method calculates the score and returns it
	public int getScore() 
	{
		// calculate score
		score = (int) ((getCoins() * 100 + getKeys() * 90 + getLives() * 80 + 
				getHealth() * 70) - (time / 50));
		
		// return score
		return score;
	} // end getScore method
	
	// setCoins method sets number of coins
	public void setCoins(int coins) 
	{
		this.coins = coins;
	} // end setCoins 
	
	// setTime method sets the time
	public void setTime(long time) 
	{
		this.time = time;
	} // end setTime method
	
	// getTime method returns the time
	public long getTime() 
	{
		return time;
	} // end getTime method
	
	// getHealth method returns the health
	public int getHealth() 
	{
		return health;
	} // end getHealth method

	// getLives method returns the number of lives
	public int getLives() 
	{
		return lives;
	} // end getLives method

	// setHealth method sets the health
	public void setHealth(int i) 
	{ 
		health = i; 
	} // end setHealth method
	
	// setLives method sets lives
	public void setLives(int i) 
	{
		lives = i; 
	} // end setLives method

	// loseHealth method decreases the health by 1
	public void loseHealth()
	{
		--health;
	} // end loseHealth method
	
	// loseHealth method decreases the lives by 1
	public void loseLife() 
	{ 
		--lives; 
	} // end loseLife method
} // end Player class