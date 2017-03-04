package game.entity.enemies;

import game.core.GamePanel;
import game.entity.Enemy;
import game.resources.Constants;
import game.resources.Resources;
import game.tileMap.TileMap;

// this class controls snail enemy stuff
public class Snail extends Enemy
{
	// constructor creates snail enemy 
	public Snail(TileMap tm, GamePanel panel, double min, double max) 
	{
		// call the parent constructor 
		super(tm, panel, min, max);

		// since the snail has different width for each frame, set an array for widths
		setWidths(new int[]{54,57});
		// since the snail has different height for each frame, set an array for heights
		setHeights(new int[]{31, 31});

		// blockageWidth will be used to detect collisions
		blockageWidth = 57;

		moveSpeed = 0.8;		// set the move speed for the snail
		maxSpeed = 0.8;			// set the maximum move speed for the snail
		fallSpeed = 0.6;		// set the fall speed for the snail
		maxFallSpeed = 10.0;	// set the maximum fall speed for the snail

		// get the snail images from resources and set the sprites
		setSprites(Resources.getEnemyImgs(Constants.SNAIL));

		// set the frames for the animation
		animation.setFrames(getSprites());
		// set delay between each frame to be 300 ms
		animation.setDelay(300);

		// set the direction of the snail so that it is facing left
		setFacingLeft();
		// set the motion of the snail so that it is going left
		isGoingLeft = true;
	} // end constructor

	// update method update the movement of snail on the screen
	public void update()
	{
		// update the width and height of the snail based on what frame is playing as an animation
		updateDimensions();

		// find the next position for the snail and update its motion
		getNextPosition();

		// check collision of the snail with tile map
		checkTileMapCollision();

		// set the position of the snail based on temporary x and temporary y
		setPosition(xTemporary, yTemporary);

		// block restrictions based on restrictions posed on the snail
		blockRestrictions();

		// check if something is blocked
		if (dx == 0 || isBlocked())
		{
			// if there is blocked way, make the snail go in the other direction
			isGoingLeft = !isGoingLeft;
			isGoingRight = !isGoingRight;
			isFacingRight = !isFacingRight;
		} // end if statement

		// update the animation
		animation.update();
	} // end update method

	// getNextPosition method moves the snail
	private void getNextPosition()
	{
		// if the snail is going left
		if (isGoingLeft)
		{
			// make the dx a negative number so that the snail goes to left
			dx -= moveSpeed;

			// if dx has reached the maximum left side speed, stop increasing (actually its decreasing) the dx value
			if (dx < -maxSpeed)
				dx = -maxSpeed;
		} // end if statement

		// if the snail is going right
		else if (isGoingRight)
		{
			// make the dx a positive number so that the snail goes to right
			dx += moveSpeed;

			// if dx has reached the maximum right side speed, stop increasing the dx value
			if (dx >= maxSpeed)
				dx = maxSpeed;
		} // end if statement

		// if snail is falling, add fall speed to dy
		if (isFalling)
			dy += fallSpeed;
	} // end getNextPosition method
} // end Snail class
