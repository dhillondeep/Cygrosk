package com.deep.game.entity.enemies;

import com.deep.game.core.GamePanel;
import com.deep.game.entity.Enemy;
import com.deep.game.resources.Constants;
import com.deep.game.resources.Resources;
import com.deep.game.tileMap.TileMap;

// this class controls fish enemy stuff
public class Fish extends Enemy 
{
	// constructor creates fish enemy 
	public Fish(TileMap tm, GamePanel panel, double min, double max) 
	{
		// call the parent constructor 
		super(tm, panel, min, max);
		
		// since the fish has different width for each frame, set an array for widths
		setWidths(new int[]{66,62});
		// since the fish has different height for each frame, set an array for heights
		setHeights(new int[]{42, 43});
		
		// blockageWidth will be used to detect collisions
		blockageWidth = 66;
		
		moveSpeed = 1.4;		// set the move speed for the fish
		maxSpeed = 1.4;			// set the maximum move speed for the fish
		
		// get the fish images from resources and set the sprites
		setSprites(Resources.getEnemyImgs(Constants.FISH));
		
		// set the frames for the animation
		animation.setFrames(getSprites());
		// set delay between each frame to be 300 ms
		animation.setDelay(300);

		// set the direction of the fish so that it is facing left
		setFacingLeft();
		// set the motion of the fish so that it is going left
		isGoingLeft = true;
	} // end constructor
 
	// update method update the movement of fish on the screen
	@Override
	public void update() 
	{
		// update the width and height of the fish based on what frame is playing as an animation
		updateDimensions();
		
		// find the next position for the fish and update its motion
		getNextPosition();
		
		// check collision of the fish with tile map
		checkTileMapCollision();
		
		// set the position of the fish based on temporary x and temporary y
		setPosition(xTemporary, yTemporary);
		
		// block restrictions based on restrictions posed on the fish
		blockRestrictions();

		// check if something is blocked
		if (dx == 0 || isBlocked())
		{
			// if there is blocked way, make the fish go in the other direction
			isGoingLeft = !isGoingLeft;
			isGoingRight = !isGoingRight;
			isFacingRight = !isFacingRight;
		} // end if statement

		// update the animation
		animation.update();
	} // end update method
	
	// getNextPosition method moves the fish
	private void getNextPosition()
	{
		// if the fish is going left
		if (isGoingLeft)
		{
			// make the dx a negative number so that the fish goes to left
			dx -= moveSpeed;

			// if dx has reached the maximum left side speed, stop increasing (actually its decreasing) the dx value
			if (dx < -maxSpeed)
				dx = -maxSpeed;
		} // end if statement

		// if the fish is going right
		else if (isGoingRight)
		{
			// make the dx a positive number so that the fish goes to right
			dx += moveSpeed;

			// if dx has reached the maximum right side speed, stop increasing the dx value
			if (dx >= maxSpeed)
				dx = maxSpeed;
		} // end if statement
	} // end getNextPosition method
} // end Fish class
