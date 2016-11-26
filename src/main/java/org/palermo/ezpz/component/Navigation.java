package org.palermo.ezpz.component;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import org.palermo.ezpz.console.Console;

public class Navigation {
	
	private Rectangle limit;
	
	public static final int INITIAL_WIDTH = 300;
	public static final int INITIAL_HEIGHT = 200;
	
	private Rectangle rectangle = new Rectangle(10, 10, INITIAL_WIDTH, INITIAL_HEIGHT);
	
	private Console console = new Console();
	
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
	
	public Rectangle getScreen() {
		Rectangle answer = null;
		if (this.limit == null) {
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize(); 
			this.limit = new Rectangle(0, 0, (int) dimension.getWidth(), (int)dimension.getHeight());
		}
		else {
			answer = this.limit; 
		}
		return answer;
	}
	
	public void clearFix() {
		this.limit = null;
	}
	
	public Rectangle getRegionOnFocus() {
		return rectangle;
	}
	
	public Rectangle getRelativeRegionOnFocus() {
		Rectangle answer = null;
		if (limit == null) {
			answer = rectangle;
		}
		else {
			answer = new Rectangle(rectangle.x - limit.x, rectangle.y - limit.y, rectangle.width, rectangle.height);
		}
		
		return answer;
	}
	
	public void setRegionOnFocus(Rectangle input) {
		if (limit == null) {
			rectangle = input;
		}
		else {
			rectangle = new Rectangle(limit.x + input.x, limit.y + input.y, input.width, input.height);
		}
		
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

		
		if (this.limit != null) {
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
				rectangle.width = rectangle.width - ((rectangle.x + rectangle.width) - (this.limit.x + this.limit.width)); 
			}
			
			if (rectangle.y + rectangle.height > this.limit.y + this.limit.height) {
				rectangle.height = rectangle.height - ((rectangle.y + rectangle.height) - (this.limit.y + this.limit.height)); 
			}
			
		}
		console.info("Limits [%d, %d, %d, %d]", rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}
	
	
	
	
	

}
