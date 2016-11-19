package org.palermo.ezpz.shell.command;

import java.awt.Rectangle;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.palermo.ezpz.config.Configuration;
import org.palermo.ezpz.console.Console;
import org.palermo.ezpz.shell.command.interfaces.Command;
import org.palermo.ezpz.utils.StringUtils;

public class RegionListCommand implements Command {
	
	private final static Pattern PATTERN = Pattern.compile("region list"); 
	
	private Console console = new Console();
	
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
		Map<String, Rectangle> regions = Configuration.DEFAULT.getRegions();
		if (regions.size() == 0) {
			console.error("There is no regions recorded!");
		}
		else {
			
			int largeNameSize = getLargeNameSize();
			for (Map.Entry<String, Rectangle> entry : regions.entrySet()) {
				Rectangle r = entry.getValue();
				console.plain("%s - [%d, %d, %d, %d]", 
						StringUtils.rpad(entry.getKey(), largeNameSize, ' '), 
						r.x, r.y, r.width, r.height);
			}
		}
	}
	
	private int getLargeNameSize() {
		int bigger = 0;
		for (String name : Configuration.DEFAULT.getRegions().keySet()) {
			bigger = Math.max(bigger, name.length());
		}
		return bigger;
	}

	
}
