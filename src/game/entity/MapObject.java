package game.entity;

import game.core.GamePanel;
import game.tileMap.Tile;
import game.tileMap.TileMap;

import java.awt.*;

// this class is the base class for every object on the tile map
public abstract class MapObject 
{
	// fields and global variables

	// tiles related
	protected TileMap tileMap;				// object of tile map
	protected int tileSize;					// size of the tile
	protected double xMap;					// x position of the tile map
	protected double yMap;					// y position of the tile map

	// position and vector related
	protected double x;						// x position of the object
	protected double y;						// y position of the object
	protected double dx;					// dx value for the object
	protected double dy;					// dy value for the object

	// dimensions related
	protected int width;					// width of the object
	protected int height;					// height of the object

	// collision related
	protected int currRow;					// current row of the object
	protected int currCol;					// current column of the object
	protected double xDestination;			// x value where object will be after update has been applied
	protected double yDestination;			// y value where object will be after update has been applied
	protected double xTemporary;			// temporary x value so that real x value is not modified
	protected double yTemporary;			// temporary y value so that real y value is not modified
	protected boolean isHittingTopLeft;		// boolean to check if the object is hitting top left tile
	protected boolean isHittingTopRight;	// boolean to check if the object is hitting top right tile
	protected boolean isHittingBottomLeft;	// boolean to check if the object is hitting bottom left tile
	protected boolean isHittingBottomRight;	// boolean to check if the object is hitting bottom right tile
	private boolean isTileCollidingUp;		// boolean to check of object is colliding with a tile up
	private boolean isTileCollidingDown;	// boolean to check of object is colliding with a tile down

	// animation related
	protected GamePanel panel;				// gamePanel object
	protected Animation animation;			// animation object
	protected int currAnimation;			// current animation being played
	protected boolean isFacingRight;		// boolean to check if the object is facing right

	// movement related
	protected boolean isGoingLeft;			// boolean to check if the object is going left
	protected boolean isGoingRight;			// boolean to check if the object is going right
	protected boolean isGoingUp;			// boolean to check if the object is going up
	protected boolean isGoingDown;			// boolean to check if the object is going down
	protected boolean isJumping;			// boolean to check if the object is jumping
	protected boolean isFalling;			// boolean to check if the object is falling
	
	// movement attributes related
	protected double moveSpeed;				// move speed of the object
	protected double maxSpeed;				// maximum speed the object can move at
	protected double stopSpeed;				// stop speed that will be applied to stop the object. Its like friction
	protected double fallSpeed;				// fall speed at which the object will fall. Its like gravity
	protected double maxFallSpeed;			// maximum speed the object can fall at. Its like terminal velocity
	protected double jumpSpeed;				// maximum speed the object can jump at
	protected double stopJumpSpeed;			// stop speed that will be applied to stop the object's jump

	// constructor initializes fields and global variables
	public MapObject(TileMap tm, GamePanel panel)
	{
		tileMap = tm;					// store the tile map
		this.panel = panel;				// store the game panel
		tileSize = tm.getTileSize();	// get the tile size and store it

		animation = new Animation();	// create an animation object
		isFacingRight = true;			// make every object start by facing right
	} // end constructor

	// isIntersecting method will check if two objects are intersecting
	public boolean isIntersecting(MapObject object)
	{
		// get the rectangle object for the current object
		Rectangle currObj = getRectangle();
		// get the rectangle object for the object that is colliding
		Rectangle otherObj = object.getRectangle();

		// return a boolean value based on if two objects are intersecting
		return currObj.intersects(otherObj);
	} // end isIntersecting method

	// contains method checks if one object contains the other
	public boolean contains(MapObject object) 
	{
		// get the rectangle object for the current object
		Rectangle currObject = getRectangle();
		// get the rectangle object for the object that is colliding
		Rectangle otherObj = object.getRectangle();

		// return a boolean value based on if one object contains the other
		return currObject.contains(otherObj);
	} // end contains method

	// getRectangle method return a rectangle object
	public Rectangle getRectangle()
	{
		// create a rectangle using the object's dimensions and return that
		return new Rectangle
				(
						(int) getLX(),
						(int) getLY(),
						width,
						height
						);
	} // end getRectangle method

	// calculateCorners method will calculate boolean to restrict object on the tile map
	public void calculateCorners(double x, double y)
	{		
		int leftTile = (int) (x - width / 2) / tileSize;			// find the left tile column
		int rightTile = (int) (x + width / 2 - 1) / tileSize;		// find the right tile column
		int topTile = (int) (y - height / 2) / tileSize;			// find the top tile row
		int bottomTile = (int) (y + height / 2 - 1) / tileSize ;	// find the bottom tile row

		// using top row and left column, find the type of the tile
		int topLeft = tileMap.getType(topTile, leftTile);
		// using top row and right column, find the type of the tile
		int topRight = tileMap.getType(topTile, rightTile);
		// using bottom row and left column, find the type of the tile
		int bottomLeft = tileMap.getType(bottomTile, leftTile);
		// using bottom row and right column, find the type of the tile
		int bottomRight = tileMap.getType(bottomTile, rightTile);

		isHittingTopLeft = topLeft == Tile.BLOCKED;				// set boolean based on if the tile is blocked
		isHittingTopRight = topRight == Tile.BLOCKED;			// set boolean based on if the tile is blocked
		isHittingBottomLeft = bottomLeft == Tile.BLOCKED;		// set boolean based on if the tile is blocked
		isHittingBottomRight = bottomRight == Tile.BLOCKED;		// set boolean based on if the tile is blocked
	} // end calculateCorners method

	// checkTileMapCollision method will set x and y value based on time map collision
	public void checkTileMapCollision()
	{
		currCol = (int) x / tileSize;		// calculate the current column
		currRow = (int) y / tileSize;		// calculate the current row

		xDestination = x + dx;				// find the x value where object will go next
		yDestination = y + dy;				// find the y value where object will go next

		xTemporary = x;						// store the temporary x value so that real x value is not tempered
		yTemporary = y;						// store the temporary y value so that real y value is not tempered

		// check collisions by checking if it will hit the y destination
		calculateCorners(x, yDestination);

		// check if object is going up
		if (dy < 0)
		{
			// if there is any collision
			if (isHittingTopLeft || isHittingTopRight)
			{
				dy = 0; // reset the dy value

				// set the temporary value such that y value is in the middle and not touching the top
				yTemporary = currRow * tileSize + height / 2;

				// since the object collided with top tile, set isTileCollidingUp equal to true
				isTileCollidingUp = true;
			}
			// if there is no collision
			else
			{
				// since there is no top collision, set isTileCollidingUp equal to false
				isTileCollidingUp = false;
				// since there is no down collision, set isTileCollidingDown equal to false
				isTileCollidingDown = false;

				// add dy to yTemporary so that object can move upwards
				yTemporary += dy;
			} // end if statement
		} // end if statement

		// check if object is going up
		if (dy > 0)
		{
			// if there is any collision
			if (isHittingBottomLeft || isHittingBottomRight)
			{
				dy = 0; // reset the dy value
				isFalling = false;	// set falling to false since the object cannot fall

				// set the temporary value such that y value is in the middle and not touching the bottom
				yTemporary = (currRow + 1) * tileSize - height / 2;

				// since the object collided with bottom tile, set isTileCollidingDown equal to true
				isTileCollidingDown = true;
			}
			else
			{
				// since there is no top collision, set isTileCollidingUp equal to false
				isTileCollidingUp = false;
				// since there is no down collision, set isTileCollidingDown equal to false
				isTileCollidingDown = false;

				// add dy to yTemporary so that object can move downwards
				yTemporary += dy;
			} // end if statement
		} // end if statement

		// check collisions by checking if it will hit the x destination
		calculateCorners(xDestination, y);

		// check if object is going left
		if (dx < 0)
		{
			// if there is any collision
			if (isHittingTopLeft || isHittingBottomLeft)
			{
				dx = 0; // reset the dx value

				// set the temporary value such that x value is in the middle and not touching the left
				xTemporary = currCol * tileSize + width / 2;
			}
			else
			{
				// add dx to yTemporary so that object can move left
				xTemporary += dx;
			}
		} // end if statement

		// check if object is going right
		if (dx > 0)
		{
			// if there is any collision
			if (isHittingTopRight || isHittingBottomRight)
			{
				dx = 0; // reset the dx value

				// set the temporary value such that x value is in the middle and not touching the right
				xTemporary = (currCol + 1) * tileSize - width / 2;

			}
			else
				// add dx to yTemporary so that object can move right
				xTemporary += dx;
		} // end if statement

		// check if object can free fall
		// only do this if the object is not falling
		if (!isFalling)
		{
			// check collisions by checking if it will have blocked tile below when it will fall
			calculateCorners(x, yDestination + 1);

			// if there is no tile that will block object movement, set falling to true
			if (!isHittingBottomLeft && !isHittingBottomRight)
				isFalling = true;
		} // end if statement
	} // end checkTileMapCollision method

	// isOneScreen method checks of the object is on screen
	public boolean isOnScreen()
	{
		return (getLX() + xMap > 0 &&
				getLX() + xMap + width < panel.getWidth() &&
				getLY() + yMap > 0 &&
				getLY() + yMap + height < panel.getHeight());
	} // end isOnScreen method

	// draw method draws the object on the screen
	public void draw(Graphics g)
	{
		setMapPosition(); // set the map position

		// draw if the object is facing right
		if (isFacingRight)
		{
			// draw the image
			// get the image from animation
			g.drawImage(
					animation.getImage(),
					(int) (getLX() + xMap), 
					(int) (getLY() + yMap),
					null
					);
		}
		// draw if the object is facing left
		else
		{
			// draw the image
			// get the image from animation
			// reflect the image so that it looks like the object is facing left
			g.drawImage(
					animation.getImage(),
					(int) (getLX() + xMap + width), 
					(int) (getLY() + yMap),
					-width,
					height,
					null
					);
		} // end if statement
	} // end draw method

	/////////////////      getters          ////////////////

	// isTileCollisionUp method will return boolean based on if object is colliding with the upwards tile
	public boolean isTileCollisionUp()
	{
		return isTileCollidingUp;
	} // end isTileCollisionUp method

	// isTileCollisionUp method will return boolean based on if object is colliding with the downwards tile
	public boolean isTileCollisionDown()
	{
		return isTileCollidingDown;
	} // end isTileCollisionDown method
	
	// getX method will return the x position of the object
	public double getX() 
	{ 
		return x; 
	} // end getX method

	// getY method will return the y position of the object
	public double getY() 
	{ 
		return y; 
	} // end getY method

	// getWidth method will return the width of the object
	public int getWidth() 
	{ 
		return width; 
	} // end getWidth method

	// getHeight method will return the height of the object
	public int getHeight() 
	{ 
		return height; 
	} // end getHeight method

	// getLX method returns the x position of the object where x is at left instead of center
	public double getLX() 
	{
		return x- width / 2;
	} // end getLX method

	// getLY method returns the x position of the object where y is at top instead of center
	public double getLY() 
	{
		return y - height / 2;
	} // end getLY method
	
	protected long getMillis(long nanoTime)
	{
		return nanoTime / 1000000;
	}


	/////////////////      setters          ////////////////

	// setPosition method will set the x and y value of the object
	public void setPosition(double x, double y) 
	{
		this.x = x;		// set x
		this.y = y;		// set y
	} // end setPosition method

	// setVector method will set the dx and dy value of the object
	public void setVector(double dx, double dy) 
	{
		this.dx = dx;	// set dx
		this.dy = dy;	// set dy
	} // end setVector method

	// setMapPosition method will set the xMap and yMap value
	public void setMapPosition() 
	{
		xMap = tileMap.getX();		// get the x value from tileMap and store it
		yMap = tileMap.getY();		// get the y value from tileMap and store it
	}
	
	// setGoingLeft method will set the object movement to left
	public void setGoingLeft(boolean b) 
	{
		this.isGoingLeft = b; 
	} // end setGoingLeft method
	
	// setGoingLeft method will set the object movement to right
	public void setGoingRight(boolean b) 
	{ 
		this.isGoingRight = b; 
	} // end setGoingRight method
	
	// setGoingLeft method will set the object movement upwards
	public void setGoingUp(boolean b) 
	{ 
		this.isGoingUp = b; 
	} // end setGoingUp method
	
	// setGoingLeft method will set the object movement downwards
	public void setGoingDown(boolean b) 
	{ 
		this.isGoingDown = b; 
	} // end setGoingDown method
} // end MapObject class