package org.palermo.ezpz.navigation;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class NavigationFacade implements Navigator {
	
	private Navigator navigation = new Navigation();
	private Navigator navigationWithLimits = null;
	
	public void setLimit(Rectangle limit) {
		this.navigationWithLimits = new NavigationWithLimits(limit);
	}
	
	public void clearLimit() {
		this.navigationWithLimits = null;
	}

	public Rectangle getScreen() {
		return navigationWithLimits == null ? navigation.getScreen() : navigationWithLimits.getScreen();
	}

	public Rectangle getRegionOnFocus() {
		return navigationWithLimits == null ? navigation.getRegionOnFocus() : navigationWithLimits.getRegionOnFocus();
	}
	
	public BufferedImage getImageOnFocus() {
		return navigationWithLimits == null ? navigation.getImageOnFocus() : navigationWithLimits.getImageOnFocus();
	}

	public void setRegionOnFocus(Rectangle input) {
		if (navigationWithLimits == null) {
			navigation.setRegionOnFocus(input);
		}
		else {
			navigationWithLimits.setRegionOnFocus(input);
		}
	}

	public void keyPressed(KeyEvent e) {
		if (navigationWithLimits == null) {
			navigation.keyPressed(e);
		}
		else {
			navigationWithLimits.keyPressed(e);
		}
	}

}
