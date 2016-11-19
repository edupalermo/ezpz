package org.palermo.ezpz.shell.command;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingWorker;

import org.palermo.ezpz.Application;
import org.palermo.ezpz.config.Configuration;
import org.palermo.ezpz.console.Console;
import org.palermo.ezpz.shell.command.interfaces.Command;
import org.palermo.ezpz.utils.ImageUtils;

public class ImageFixCommand implements Command {
	
	private final static Console console = new Console();

	private final static Pattern PATTERN = Pattern.compile("image fix (\\w+)");
	
	public boolean executed(String command) {
		boolean executed = false;
		Matcher matcher = PATTERN.matcher(command);
		if (matcher.matches()) {
			executed = true;
			this.execute(matcher.group(1));
		}
		return executed;
	}

	private void execute(String imageName) {
		
		BufferedImage bufferedImage = Configuration.DEFAULT.loadImage(imageName); 

		if (bufferedImage == null) {
			console.error("Image [%s] not recorded! ", imageName);
		} else {
			InternalWorker myWorker = new InternalWorker(imageName, bufferedImage);
	        myWorker.execute();
		}
	}
	
	private static class InternalWorker extends SwingWorker<Void, String> {
		
		private final BufferedImage search;
		private final String imageName;
		
		public InternalWorker(String imageName, BufferedImage search) {
			this.search = search;
			this.imageName = imageName;
		}

		@Override
		protected Void doInBackground() throws Exception {
			Point location = ImageUtils.locate(Application.robot.createScreenCapture(ImageUtils.getScreenSize()), this.search);

			if (location == null) {
				console.error("Not found slice of screen that match [%s]", imageName);
			} else {
				console.info("Window found on (%d, %d)!", location.x, location.y);
			}
			
			return null;
		}
		
	}

}
