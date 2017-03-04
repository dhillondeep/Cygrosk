package game.core;

import game.resources.Constants;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

// this class creates a panel to show introduction animation
public class IntroPanel extends JPanel
{
	// serialVersionUID 
	private static final long serialVersionUID = 3121500278691575174L;

	// constructor create a panel to show introduction animation
	public IntroPanel(int width, int height) 
	{	
		// create a dimension for the size of the panel
		// set the size of the panel
		setPreferredSize(new Dimension(width, height));
		
		// show introduction animation
		showIntro();
	} // end Constructor

	// showIntro method shows the introduction animation on the screen
	public void showIntro()
	{
		// get the URL for the animation gif file stored in the resources
		URL url = this.getClass().getResource(Constants.LOC_INTRO);

		// check if URL is null or not
		if (url != null)
		{
			// if URL is not null, create an object of ImageIcon class and provide it with the image URL
			ImageIcon image = new ImageIcon(url);

			// create a label and then add it to the panel
			// this label will have animation image on it
			JLabel label = new JLabel(image);

			// add label to the panel so that it can be displayed
			this.add(label);
		} // end if statement
	} // end showIntro class
} // end IntroPanel class
