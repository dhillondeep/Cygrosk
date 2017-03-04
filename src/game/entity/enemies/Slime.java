package game.entity.enemies;

import game.core.GamePanel;
import game.entity.Enemy;
import game.resources.Constants;
import game.resources.Resources;
import game.tileMap.TileMap;

// this class controls slime enemy stuff
public class Slime extends Enemy
{
	// constructor creates slime enemy 
	public Slime(TileMap tm, GamePanel panel, double min, double max) 
	{
		// call the parent constructor 
		super(tm, panel, min, max);

		// since the slime has different width for each frame, set an array for widths
		setWidths(new int[]{50,51});
		// since the slime has different height for each frame, set an array for heights
		setHeights(new int[]{28, 28});

		// blockageWidth will be used to detect collisions
		blockageWidth = 51;

		moveSpeed = 0.3;		// set the move speed for the slime
		maxSpeed = 0.3;			// set the maximum move speed for the slime
		fallSpeed = 0.2;		// set the fall speed for the slime
		maxFallSpeed = 10.0;	// set the maximum fall speed for the slime

		// get the slime images from resources and set the sprites
		setSprites(Resources.getEnemyImgs(Constants.SLIME));

		// set the frames for the animation
		animation.setFrames(getSprites());
		// set delay between each frame to be 300 ms
		animation.setDelay(300);

		// set the direction of the slime so that it is facing left
		setFacingLeft();
		// set the motion of the slime so that it is going left
		isGoingLeft = true;
	} // end constructor

	// update method update the movement of slime on the screen
	public void update()
	{
		// update the width and height of the slime based on what frame is playing as an animation
		updateDimensions();

		// find the next position for the slime and update its motion
		getNextPosition();

		// check collision of the slime with tile map
		checkTileMapCollision();

		// set the position of the slime based on temporary x and temporary y
		setPosition(xTemporary, yTemporary);

		// block restrictions based on restrictions posed on the slime
		blockRestrictions();

		// check if something is blocked
		if (dx == 0 || isBlocked())
		{
			// if there is blocked way, make the slime go in the other direction
			isGoingLeft = !isGoingLeft;
			isGoingRight = !isGoingRight;
			isFacingRight = !isFacingRight;
		} // end if statement

		// update the animation
		animation.update();
	} // end update method

	// getNextPosition method moves the slime
	private void getNextPosition()
	{
		// if the slime is going left
		if (isGoingLeft)
		{
			// make the dx a negative number so that the slime goes to left
			dx -= moveSpeed;

			// if dx has reached the maximum left side speed, stop increasing (actually its decreasing) the dx value
			if (dx < -maxSpeed)
				dx = -maxSpeed;
		} // end if statement

		// if the slime is going right
		else if (isGoingRight)
		{
			// make the dx a positive number so that the slime goes to right
			dx += moveSpeed;

			// if dx has reached the maximum right side speed, stop increasing the dx value
			if (dx >= maxSpeed)
				dx = maxSpeed;
		} // end if statement

		// if slime is falling, add fall speed to dy
		if (isFalling)
			dy += fallSpeed;
	} // end getNextPosition method
} // end Slime class
