package org.palermo.ezpz.navigation;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.palermo.ezpz.Application;
import org.palermo.ezpz.console.Console;

public class NavigationWithLimits implements Navigator {

	private final Rectangle limit;

	public static final int INITIAL_WIDTH = 300;
	public static final int INITIAL_HEIGHT = 200;

	private Rectangle rectangle = new Rectangle(10, 10, INITIAL_WIDTH, INITIAL_HEIGHT);

	private Console console = new Console();

	public NavigationWithLimits(Rectangle limit) {
		this.limit = limit;
	}

	public Rectangle getScreen() {
		return this.limit;
	}

	public Rectangle getRegionOnFocus() {
		return new Rectangle(rectangle.x - limit.x, rectangle.y - limit.y, rectangle.width, rectangle.height);
	}

	public BufferedImage getImageOnFocus() {
		BufferedImage bufferedImage = null;
		synchronized (Application.robot) {
			bufferedImage = Application.robot.createScreenCapture(this.rectangle);
		}
		return bufferedImage;
	}

	public void setRegionOnFocus(Rectangle input) {
		rectangle = new Rectangle(limit.x + input.x, limit.y + input.y, input.width, input.height);
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
		
		console.info("Limits [%d, %d, %d, %d]", rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}

}
