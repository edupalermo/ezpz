package org.palermo.ezpz.navigation;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.palermo.ezpz.Application;
import org.palermo.ezpz.console.Console;

public class Navigation implements Navigator {
	
	public static final int INITIAL_WIDTH = 300;
	public static final int INITIAL_HEIGHT = 200;
	
	private Rectangle rectangle = new Rectangle(10, 10, INITIAL_WIDTH, INITIAL_HEIGHT);
	
	private Console console = new Console();
	
	public Rectangle getScreen() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize(); 
		return new Rectangle(0, 0, (int) dimension.getWidth(), (int)dimension.getHeight());
	}
	
	public Rectangle getRegionOnFocus() {
		return rectangle;
	}
	
	public void setRegionOnFocus(Rectangle input) {
		rectangle = input;
	}
	
	public BufferedImage getImageOnFocus() {
		BufferedImage bufferedImage = null;
		synchronized (Application.robot) {
			bufferedImage = Application.robot.createScreenCapture(this.rectangle);
		}
		return bufferedImage;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		int factor = 1;
		if (e.isControlDown()) {
			factor = 10;
		}

		if (e.isShiftDown()) {
			if (key == KeyEvent.VK_KP_LEFT || key == KeyEvent.VK_LEFT) {
				rectangle.width = rectangle.width - factor;
			}
			if (key == KeyEvent.VK_KP_RIGHT || key == KeyEvent.VK_RIGHT) {
				rectangle.width = rectangle.width + factor;
			}
			if (key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_UP) {
				rectangle.height = rectangle.height - factor;
			}
			if (key == KeyEvent.VK_KP_DOWN || key == KeyEvent.VK_DOWN) {
				rectangle.height = rectangle.height + factor;
			}
		} else {
			if (key == KeyEvent.VK_KP_LEFT || key == KeyEvent.VK_LEFT) {
				rectangle.x = rectangle.x - factor;
			}
			if (key == KeyEvent.VK_KP_RIGHT || key == KeyEvent.VK_RIGHT) {
				rectangle.x = rectangle.x + factor;
			}
			if (key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_UP) {
				rectangle.y = rectangle.y - factor;
			}
			if (key == KeyEvent.VK_KP_DOWN || key == KeyEvent.VK_DOWN) {
				rectangle.y = rectangle.y + factor;
			}
		}


		if (rectangle.x < 0) {
			rectangle.x = 0;
		}
		if (rectangle.width < 0) {
			rectangle.width = 0;
		}
		if (rectangle.height < 0) {
			rectangle.height = 0;
		}
		
		console.info("Limits [%d, %d, %d, %d]", rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}

}
