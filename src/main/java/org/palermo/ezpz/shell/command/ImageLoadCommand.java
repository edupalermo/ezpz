package org.palermo.ezpz.shell.command;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.palermo.ezpz.Application;
import org.palermo.ezpz.config.Configuration;
import org.palermo.ezpz.console.Console;
import org.palermo.ezpz.shell.command.interfaces.Command;

public class ImageLoadCommand implements Command {
	
	private final static Pattern PATTERN = Pattern.compile("image load (\\w+)"); 
	
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
		Map<String, byte[]> imageMap = Configuration.DEFAULT.getImages();
		if (!imageMap.containsKey(imageName)) {
			console.error("Image [%s] not found!", imageName);
		}
		else {
			Application.mainWindow.loadImage(Configuration.DEFAULT.loadImage(imageName));
			console.info("Image [%s] loaded.", imageName);
		}
	}
}
