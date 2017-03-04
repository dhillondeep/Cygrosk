package com.deep.game.handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// this class handles all the keyboard inputs
public class Keyboard implements KeyListener
{
	// total number of keys
	private final static int NUM_OF_KEYS = 14;
	
	// boolean array for all the keys
	private final static boolean [] keyStates = new boolean[NUM_OF_KEYS];
	
	// constants for each single key
	public final static int UP = 0;
	public final static int DOWN = 1;
	public final static int LEFT = 2;
	public final static int RIGHT = 3;
	public final static int SPACEBAR = 4;
	public final static int ESC = 5;
	public final static int A = 6;
	public final static int W = 7;
	public final static int S = 8;
	public final static int D = 9;
	public final static int X = 10;
	public final static int O = 11;
	public final static int C = 12;
	public final static int ENTER = 13;
	
	// setKey method will set the key based on keyCode and state
	public void setKey(int keyCode, boolean state)
	{
		if (keyCode == KeyEvent.VK_UP) keyStates[UP] = state;
		else if (keyCode == KeyEvent.VK_DOWN) keyStates[DOWN] = state;
		else if (keyCode == KeyEvent.VK_LEFT) keyStates[LEFT] = state;
		else if (keyCode == KeyEvent.VK_RIGHT) keyStates[RIGHT] = state;
		else if (keyCode == KeyEvent.VK_SPACE) keyStates[SPACEBAR] = state;
		else if (keyCode == KeyEvent.VK_ESCAPE) keyStates[ESC] = state;
		else if (keyCode == KeyEvent.VK_A) keyStates[A] = state;
		else if (keyCode == KeyEvent.VK_W) keyStates[W] = state;
		else if (keyCode == KeyEvent.VK_S) keyStates[S] = state;
		else if (keyCode == KeyEvent.VK_D) keyStates[D] = state;
		else if (keyCode == KeyEvent.VK_X) keyStates[X] = state;
		else if (keyCode == KeyEvent.VK_O) keyStates[O] = state;
		else if (keyCode == KeyEvent.VK_C) keyStates[C] = state;
		else if (keyCode == KeyEvent.VK_ENTER) keyStates[ENTER] = state;
	} // end setKey
	
	// isKeyPressed method returns true or false based on if particular key is pressed or not
	public static boolean isKeyPressed(int keyCode)
	{
		// return value from the array
		return keyStates[keyCode];
	} // end isKeyPressed method
	
	@Override
	public void keyTyped(KeyEvent e)
	{
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		// if key is pressed, set the particular key true
		setKey(e.getKeyCode(), true);
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		// if key is releases, set the particular key false
		setKey(e.getKeyCode(), false);
	}
} // end Keyboard class
