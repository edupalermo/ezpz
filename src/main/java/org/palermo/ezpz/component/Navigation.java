package org.palermo.ezpz.component;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Navigation {
	
	private Rectangle limit;
	
	private Rectangle rectangle = new Rectangle(10, 10, 100, 100);
	
	public void fixPoint(int x, int y) {
		if (limit == null) {
			limit = new Rectangle();
		}
		this.limit.x = x;
		this.limit.y = y;
	}
	
	public void fixLimits(int width, int height) {
		if (limit == null) {
			limit = new Rectangle();
			this.limit.x = 0;
			this.limit.y = 0;
		}
		this.limit.width = width;
		this.limit.height = height;
	}
	
	public void clearRegion() {
		this.limit = null;
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

		
		if (this.limit == null) {
			if (rectangle.x < 0) {
				rectangle.x = 0;
			}
			if (rectangle.y < 0) {
				rectangle.y = 0;
			}
		}
		else {
			if (rectangle.x < this.limit.x) {
				rectangle.x = limit.x;
			}
			
			if (rectangle.x > this.limit.x + this.limit.width) {
				rectangle.x = this.limit.x + this.limit.width;
			}
			
			if (rectangle.y < this.limit.y) {
				rectangle.y = limit.y;
			}
			
			if (rectangle.y > this.limit.y + this.limit.height) {
				rectangle.y = this.limit.y + this.limit.height;
			}
			
			
			if (rectangle.x + rectangle.width > this.limit.x + this.limit.width) {
				rectangle.width = (this.limit.x + this.limit.width) - (rectangle.x + rectangle.width); 
			}
			
			if (rectangle.y + rectangle.height > this.limit.y + this.limit.height) {
				rectangle.height = (rectangle.y + rectangle.height) - (this.limit.y + this.limit.height); 
			}
		}
		
		
		
		
		
	}
	
	
	
	
	

}
