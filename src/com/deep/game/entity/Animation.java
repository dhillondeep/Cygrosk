package com.deep.game.entity;

import java.awt.image.BufferedImage;

// this class handles the animation of objects on tile map
public class Animation
{
	// fields and global variables
	
	private BufferedImage[] frames;		// frames to draw as a part of animation
	private int currentFrame;			// current frame that is being played
	
	private long startTime;				// time when the current frame was changed
	private long delay;					// delay between frame changed
	
	// setFrames method sets the frames
	public void setFrames(BufferedImage [] frames)
	{	
		// set the frames based on the frames provided
		this.frames = frames;
		// set the current frame to the first frame
		currentFrame = 0;
		// get the start time from the system in nanoseconds
		startTime = System.nanoTime();
	} // end setFrames method
	
	// setDelay methods sets the delay between frames
	public void setDelay(long delay)
	{
		// set the delay based on delay provided
		this.delay = delay;
	} // end setDelay method
	
	// update method updates the animation
	public void update()
	{
		// if delay is -1, do not update anything and return
		if (delay == -1) return;
		
		// find the elapsed time using current time and the start time
		// convert the time to milliseconds 
		long elapsed = (System.nanoTime() - startTime) / 1000000;	
		
		// if elapsed time is greater than delay
		if (elapsed >= delay)
		{
			// increment the current frame variable
			currentFrame++;
			// reset the start time by getting the current time
			startTime = System.nanoTime();
		} // end if statement
		
		// if last frame has passed, loop back to first frame
		if (currentFrame == frames.length)
			currentFrame = 0;
	} // end update method
	
	// getCurrenetFrame method returns the current frame number
	public int getCurrentFrame() 
	{
		return currentFrame;
	} // end getCurrentFrame method
	
	// getImage method returns the image of the current frame
	public BufferedImage getImage()
	{
		return frames[currentFrame];
	} // end getImage method
} // end Animation class
