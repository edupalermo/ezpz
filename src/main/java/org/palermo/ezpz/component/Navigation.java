package org.palermo.ezpz.component;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Navigation {
	
	private Point point;
	
	private Rectangle rectangle = new Rectangle(10, 10, 100, 100);
	
	private void setPoint(Point point) {
		this.point = point;
	}
	
	public Rectangle getRectangle() {
		return rectangle;
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
		if (rectangle.y < 0) {
			rectangle.y = 0;
		}
		
	}
	
	
	
	
	

}
