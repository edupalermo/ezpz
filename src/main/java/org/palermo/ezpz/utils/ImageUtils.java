package org.palermo.ezpz.utils;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class ImageUtils {
	
	//private static Console console = new Console();
	
	public static Point locate(BufferedImage source, BufferedImage search) {
		Point location = null;
		
		double minTax = Double.MAX_VALUE;
		
		//double higherDifference = 3 * 255 * (search.getWidth() * search.getHeight());
		
		for (int y = 0; y < source.getHeight() - (search.getHeight() - 1); y++) {
			for (int x = 0; x < source.getWidth() - (search.getWidth() - 1); x++) {
				
				double newTax = checkSimilarity(x, y, source, search, minTax);
				
				if (newTax < minTax) {
					minTax = newTax;
					// console.info("New minTax (%d, %d) %1.6f\\%", x, y, (minTax / higherDifference));
					location = new Point(x, y);
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

	public static boolean areTheSame(BufferedImage source, BufferedImage search) {
		return areTheSame(0, 0, source, search);
	}
	
	public static double checkSimilarity(int px, int py, BufferedImage source, BufferedImage search, double kill) {
		double tax = 0;
		
		loop: for (int x = 0; x < search.getWidth(); x++) {
			for (int y = 0; y < search.getHeight(); y++) {
				int sourceRgb = source.getRGB(x + px, y + py);
				int searchRgb = search.getRGB(x, y);
				
				tax += Math.abs((sourceRgb & 0xFF) - (searchRgb & 0xFF)) + 
						Math.abs(((sourceRgb >> 8) & 0xFF) - ((searchRgb >> 8) & 0xFF)) + 
						Math.abs(((sourceRgb >> 16) & 0xFF) - ((searchRgb >> 16) & 0xFF));
				
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
