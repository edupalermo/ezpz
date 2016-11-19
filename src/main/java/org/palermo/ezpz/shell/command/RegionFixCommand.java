package org.palermo.ezpz.shell.command;

import java.awt.Rectangle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.palermo.ezpz.Application;
import org.palermo.ezpz.config.Configuration;
import org.palermo.ezpz.console.Console;
import org.palermo.ezpz.shell.command.interfaces.Command;

public class RegionFixCommand implements Command {
	
	private final static Console console = new Console();

	private final static Pattern PATTERN = Pattern.compile("region fix (\\w+)");
	
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
		
		Rectangle rectangle = Configuration.DEFAULT.getRegion(imageName);
		
		if (rectangle == null) {
			console.error("Region [%s] not recorded! ", imageName);
		} else {
			Application.mainWindow.getNavigation().fixLimits(rectangle.width, rectangle.height);
		}
	}
	

}
