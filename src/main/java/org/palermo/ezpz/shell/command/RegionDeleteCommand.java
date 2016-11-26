package org.palermo.ezpz.shell.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.palermo.ezpz.config.Configuration;
import org.palermo.ezpz.console.Console;
import org.palermo.ezpz.shell.command.interfaces.Command;

public class RegionDeleteCommand implements Command {
	
	private final static Pattern PATTERN = Pattern.compile("region delete (\\w+)"); 
	
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

	
	private void execute(String name) {
		if (Configuration.DEFAULT.deleteRegion(name)) {
			console.info("Region [%s] removed and database updated.", name);
		}
		else {
			console.error("Region [%s] not found!", name);
		}
	}
}
