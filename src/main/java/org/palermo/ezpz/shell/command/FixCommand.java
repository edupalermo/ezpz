package org.palermo.ezpz.shell.command;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingWorker;

import org.palermo.ezpz.Application;
import org.palermo.ezpz.config.ImageDao;
import org.palermo.ezpz.config.RegionDao;
import org.palermo.ezpz.console.Console;
import org.palermo.ezpz.shell.command.interfaces.Command;
import org.palermo.ezpz.utils.ImageUtils;

public class FixCommand implements Command {
	
	private final static Console console = new Console();

	private final static Pattern PATTERN = Pattern.compile("fix (\\w+) (\\w+)");
	
	public boolean executed(String command) {
		boolean executed = false;
		Matcher matcher = PATTERN.matcher(command);
		if (matcher.matches()) {
			executed = true;
			this.execute(matcher.group(1), matcher.group(2));
		}
		return executed;
	}

	private void execute(String imageName, String regionName) {
		
		BufferedImage bufferedImage = ImageDao.get(imageName); 
		if (bufferedImage == null) {
			console.error("Image [%s] not recorded! ", imageName);
			return;
		}
		
		Rectangle region = RegionDao.get(regionName);
		
		if (region == null) {
			console.error("Region [%s] not recorded! ", imageName);
			return;
		}

		InternalWorker myWorker = new InternalWorker(imageName, bufferedImage, region);
        myWorker.execute();
	}
	
	private static class InternalWorker extends SwingWorker<Void, String> {
		
		private final BufferedImage search;
		private final String imageName;
		
		private final Rectangle region;
		
		public InternalWorker(String imageName, BufferedImage search, Rectangle region) {
			this.search = search;
			this.imageName = imageName;
			
			this.region = region;
		}

		@Override
		protected Void doInBackground() throws Exception {
			BufferedImage screen = Application.robot.createScreenCapture(ImageUtils.getScreenSize());
			Point location = locate(screen, this.search);
			if (location == null) {
				this.publish(String.format("Not found slice of screen that match [%s]", imageName));
			} else {
				Rectangle limit = new Rectangle(location.x, location.y + this.search.getHeight(), (int) this.region.getWidth(), (int) this.region.getHeight());
				Application.mainWindow.getNavigation().setLimit(limit);
				this.publish(String.format("Image fixed on (%d, %d, %d, %d)!", limit.x, limit.y, limit.width, limit.height));
			}
			
			return null;
		}
		
		private Point locate(BufferedImage source, BufferedImage search) {
			Point location = null;
			
			double minTax = Double.MAX_VALUE;
			
			double higherDifference = 3 * 255 * (search.getWidth() * search.getHeight());
			
			outFor: for (int y = 0; y < source.getHeight() - (search.getHeight() - 1); y++) {
				for (int x = 0; x < source.getWidth() - (search.getWidth() - 1); x++) {
					
					double newTax = ImageUtils.checkSimilarity(x, y, source, search, minTax);
					
					if (newTax < minTax) {
						minTax = newTax;
						this.publish(String.format("New minTax (%d, %d) %1.6f%%", x, y, (minTax / higherDifference)));
						location = new Point(x, y);
					}
					
					if (minTax == 0) {
						break outFor;
					}
				}
			}
			return location;
			
		}


		@Override
		protected void process(List<String> messages) {
			for (String s : messages) {
				console.info(s);
			}
		}
		
	}

}
