package org.palermo.ezpz.shell.command;

import java.awt.image.BufferedImage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.palermo.ezpz.component.DisplayImageWindow;
import org.palermo.ezpz.config.ImageDao;
import org.palermo.ezpz.console.Console;
import org.palermo.ezpz.shell.command.interfaces.Command;

public class MaskShowCommand implements Command {
	
	private final static Pattern PATTERN = Pattern.compile("mask show (\\w+)"); 
	
	private Console console = new Console();

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
		BufferedImage bufferedImage = ImageDao.get(imageName);
		if (bufferedImage == null) {
			console.error("Image [%s] not found!", imageName);
		}
		else {
			DisplayImageWindow diw = new DisplayImageWindow(imageName, bufferedImage);
			diw.pack();
			diw.setVisible(true);
			console.info("Image [%s] loaded.", imageName);
		}
	}
}
