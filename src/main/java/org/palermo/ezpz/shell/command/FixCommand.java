package org.palermo.ezpz.shell.command;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.palermo.ezpz.Application;
import org.palermo.ezpz.config.Configuration;
import org.palermo.ezpz.log.Logger;
import org.palermo.ezpz.shell.command.interfaces.Command;
import org.palermo.ezpz.utils.ImageUtils;

public class FixCommand implements Command {
	
	private final static Logger logger = new Logger();

	private final static Pattern PATTERN = Pattern.compile("fix");
	private final static String IMAGE_APPLICATION_BAR = "applicationBar";
	
	private final Configuration configuration;

	public FixCommand(Configuration configuration) {
		this.configuration = configuration;
	}

	public boolean executed(String command) {
		boolean executed = false;

		Matcher matcher = PATTERN.matcher(command);

		if (matcher.matches()) {
			executed = true;
			this.execute();
		}

		return executed;
	}

	private void execute() {

		BufferedImage search = this.configuration.loadImage(IMAGE_APPLICATION_BAR);

		if (search == null) {
			logger.error("Image [%s] not recorded! ", IMAGE_APPLICATION_BAR);
		} else {
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			Point location = ImageUtils.locate(Application.robot.createScreenCapture(ImageUtils.getScreenSize()), search);

			if (location == null) {
				logger.error("Not found slice of screen that match [%s]", IMAGE_APPLICATION_BAR);
			} else {
				logger.info("Window found on (%d, %d)!", location.x, location.y);
			}
		}
	}

}
