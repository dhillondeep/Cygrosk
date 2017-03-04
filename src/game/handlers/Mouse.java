package com.deep.game.handlers;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// this class handles all the mouse related stuff
public class Mouse extends MouseAdapter
{
	// stores number of clicks for left button on mouse
	private static int leftButtonClicks = 0;
	
	// stores the location of the mouse cursor
	private static Point mouseLocation;

	public void mouseMoved(MouseEvent e)
	{
		// if mouse has been moved, make a point of the location of the cursor
		mouseLocation = new Point(e.getX(), e.getY());
	}

	@Override
	public void mouseDragged(MouseEvent e) 
	{
		// if mouse has dragged, make a point of the location of the cursor
		mouseLocation = new Point(e.getX(), e.getY());
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		// if mouse has entered the screen, make a point of the location of the cursor
		mouseLocation = new Point(e.getX(), e.getY());
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		// if mouse has exited the screen, make a point of the location of the cursor
		mouseLocation = new Point(e.getX(), e.getY());
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		switch (e.getButton()) 
		{
		// if left button on the mouse is clicked, store the number of clicks
		case MouseEvent.BUTTON1:
			leftButtonClicks = e.getClickCount();
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		switch (e.getButton()) 
		{
		// if left button on the mouse is released, reset the number of clicks to 0
		case MouseEvent.BUTTON1:
			leftButtonClicks = 0;
			break;
		}
	}

	// getMouseLocation method returns the location of the mouse cursor
	public static Point getMouseLocation() 
	{
		// if there is valid mouse location
		if (mouseLocation != null)
			// if location is valid, return the mouse location
			return mouseLocation;
		
		// return negative coordinates if the location is not valid
		return new Point(-1, -1);
	} // end getMouseLocation method
	
	// isLeftButtonPressed method returns true or false based on if left button on the mouse is clicked
	public static boolean isLeftButtonPressed()
	{
		// return true if number of clicks for left button are more than 0
		if (leftButtonClicks > 0)
			return true;

		// return false if number of clicks are 0
		return false;
	} // end isLeftButtonPressed method
} // end Mouse class
