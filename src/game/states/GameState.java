package com.deep.game.states;

import java.awt.Graphics;

// this is a base class for every game state
public abstract class GameState
{
	// stores GameStateManager object
	protected final GameStateManager gsm;
	
	// constructor initializes game state manager
	public GameState(GameStateManager gsm)
	{
		this.gsm = gsm;
	} // end constructor
	
	// initialize method in every game state
	public abstract void initialize();
	// draw method in every game state
	public abstract void draw(Graphics g);
	// update method in every game state
	public abstract void update();
	// input handler in every game state
	public abstract void inputHandler();
} // end GameState class
