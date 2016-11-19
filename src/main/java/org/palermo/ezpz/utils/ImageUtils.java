package org.palermo.ezpz.utils;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.palermo.ezpz.console.Console;

public class ImageUtils {
	
	private static Console logger = new Console();
	
	public static Point locate(BufferedImage source, BufferedImage search) {
		Point location = null;
		
		double minTax = Double.MAX_VALUE;
		
		for (int y = 0; y < source.getHeight() - (search.getHeight() - 1); y++) {
			for (int x = 0; x < source.getWidth() - (search.getWidth() - 1); x++) {
				
				double newTax = checkSimilarity(x, y, source, search, minTax);
				
				if (newTax < minTax) {
					minTax = newTax;
					logger.info("New minTax (%d, %d) %3.0f",x, y, minTax);
					location = new Point(x, y);
				}
			}
		}
		return location;
		
	}

	public static Point oldLocate(BufferedImage source, BufferedImage search) {
		Point location = null;
		
		search : for (int y = 0; y < source.getHeight() - (search.getHeight() - 1); y++) {
			for (int x = 0; x < source.getWidth() - (search.getWidth() - 1); x++) {
				if (areTheSame(x, y, source, search)) {
					location = new Point(x, y);
					break search;
				}
			}
		}
		return location;
		
	}

	private static boolean areTheSame(int px, int py, BufferedImage source, BufferedImage search) {
		boolean same = true;
		
		search : for (int x = 0; x < search.getWidth(); x++) {
			for (int y = 0; y < search.getHeight(); y++) {
				if (source.getRGB(x + px, y + py) != search.getRGB(x, y)) {
					same = false;
					break search;
				}
			}
		}
		return same;
	}
	
	private static double checkSimilarity(int px, int py, BufferedImage source, BufferedImage search, double kill) {
		double tax = 0;
		
		loop: for (int x = 0; x < search.getWidth(); x++) {
			for (int y = 0; y < search.getHeight(); y++) {
				int sourceRgb = source.getRGB(x + px, y + py);
				int searchRgb = search.getRGB(x, y);
				
				tax += Math.sqrt(Math.pow((sourceRgb & 0xFF) - (searchRgb & 0xFF), 2) + 
						Math.pow(((sourceRgb >> 8) & 0xFF) - ((searchRgb >> 8) & 0xFF), 2) + 
						Math.pow(((sourceRgb >> 16) & 0xFF) - ((searchRgb >> 16) & 0xFF), 2));
				
				if (tax > kill) {
					break loop;
				}
			}
		}
		return tax;
	}
	
	public static Rectangle getScreenSize() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		DisplayMode displayMode = gd.getDisplayMode();
		
		Rectangle rectangle = new Rectangle(displayMode.getWidth(), displayMode.getHeight());
		return rectangle;
	}
}
