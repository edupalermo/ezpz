package org.palermo.ezpz.shell.command;

import java.awt.Rectangle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.palermo.ezpz.Application;
import org.palermo.ezpz.config.Configuration;
import org.palermo.ezpz.console.Console;
import org.palermo.ezpz.shell.command.interfaces.Command;

public class RegionSaveCommand implements Command {
	
	private final static Pattern PATTERN = Pattern.compile("region save (\\w+)"); 
	
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

	
	private void execute(String regionName) {
		Rectangle r = Application.mainWindow.getRegionOnFocus();
		Configuration.DEFAULT.saveRegion(regionName, r);
		console.info("Region [%s] saved with specs [%d, %d, %d, %d].", regionName, r.x, r.y, r.width, r.height);
	}
}
