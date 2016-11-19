package org.palermo.ezpz.shell.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.palermo.ezpz.config.Configuration;
import org.palermo.ezpz.console.Console;
import org.palermo.ezpz.shell.command.interfaces.Command;

public class ImageDeleteCommand implements Command {
	
	private final static Pattern PATTERN = Pattern.compile("image delete (\\w+)"); 
	
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
		if (Configuration.DEFAULT.removeImage(imageName)) {
			console.info("Image [%s] removed and database updated.", imageName);
		}
		else {
			console.error("Image [%s] not found!", imageName);
		}
	}
}
