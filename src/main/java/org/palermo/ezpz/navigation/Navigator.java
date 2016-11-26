package org.palermo.ezpz.navigation;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public interface Navigator {
	
	Rectangle getScreen();
	
	Rectangle getRegionOnFocus();
	
	BufferedImage getImageOnFocus();
	
	void setRegionOnFocus(Rectangle input);
	
	void keyPressed(KeyEvent e);
}
