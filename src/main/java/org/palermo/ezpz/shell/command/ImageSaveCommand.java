package org.palermo.ezpz.shell.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.palermo.ezpz.Application;
import org.palermo.ezpz.config.Configuration;
import org.palermo.ezpz.console.Console;
import org.palermo.ezpz.shell.command.interfaces.Command;

public class ImageSaveCommand implements Command {
	
	private final static Pattern PATTERN = Pattern.compile("image save (\\w+)"); 
	
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
		Configuration.DEFAULT.saveImage(imageName, Application.mainWindow.getNavigation().getImageOnFocus());
		console.info("Image [%s] saved with size [%d] bytes.", imageName, Configuration.DEFAULT.getImages().get(imageName).length);

	}
}
