package game.entity.enemies;

import game.core.GamePanel;
import game.entity.Enemy;
import game.resources.Constants;
import game.resources.Resources;
import game.tileMap.TileMap;

//this class controls fly enemy stuff
public class Fly extends Enemy
{
	// constructor creates fly enemy 
	public Fly(TileMap tm, GamePanel panel, double min, double max) 
	{
		// call the parent constructor 
		super(tm, panel, min, max);

		// since the fly has different width for each frame, set an array for widths
		setWidths(new int[]{72,75});
		// since the fly has different height for each frame, set an array for heights
		setHeights(new int[]{36, 31});

		// blockageWidth will be used to detect collisions
		blockageWidth = 75;

		moveSpeed = 1.8;		// set the move speed for the fish
		maxSpeed = 1.8;			// set the maximum move speed for the fish

		// get the fly images from resources and set the sprites
		setSprites(Resources.getEnemyImgs(Constants.FLY));

		// set the frames for the animation
		animation.setFrames(getSprites());
		// set delay between each frame to be 200 ms
		animation.setDelay(200);

		// set the direction of the fly so that it is facing left
		setFacingLeft();
		// set the motion of the fly so that it is going left
		isGoingLeft = true;
	} // end constructor

	// update method update the movement of fly on the screen
	@Override
	public void update() 
	{
		// update the width and height of the fly based on what frame is playing as an animation
		updateDimensions();

		// find the next position for the fly and update its motion
		getNextPosition();

		// check collision of the fly with tile map
		checkTileMapCollision();

		// set the position of the fly based on temporary x and temporary y
		setPosition(xTemporary, yTemporary);

		// block restrictions based on restrictions posed on the fly
		blockRestrictions();

		// check if something is blocked
		if (dx == 0 || isBlocked())
		{
			// if there is blocked way, make the fly go in other direction
			isGoingLeft = !isGoingLeft;
			isGoingRight = !isGoingRight;
			isFacingRight = !isFacingRight;
		} // end if statement

		// update the animation
		animation.update();
	} // end update method

	// getNextPosition method moves the fly
	private void getNextPosition()
	{
		// if the fly is going left
		if (isGoingLeft)
		{
			// make the dx a negative number so that the fly goes to left
			dx -= moveSpeed;

			// if dx has reached the maximum left side speed, stop increasing (actually its decreasing) the dx value
			if (dx < -maxSpeed)
				dx = -maxSpeed;
		} // end if statement

		// if the fly is going right
		else if (isGoingRight)
		{
			// make the dx a positive number so that the fly goes to right
			dx += moveSpeed;

			// if dx has reached the maximum right side speed, stop increasing the dx value
			if (dx >= maxSpeed)
				dx = maxSpeed;
		} // end if statement
	} // end getNextPosition method
} // end Fly class
