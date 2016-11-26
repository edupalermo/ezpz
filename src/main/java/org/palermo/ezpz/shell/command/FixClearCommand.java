package org.palermo.ezpz.shell.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.palermo.ezpz.Application;
import org.palermo.ezpz.console.Console;
import org.palermo.ezpz.shell.command.interfaces.Command;

public class FixClearCommand implements Command {
	
	private final static Console console = new Console();

	private final static Pattern PATTERN = Pattern.compile("fix clear");
	
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
		Application.mainWindow.getNavigation().clearLimit();
		console.info("Fix cleared");
		
	}

}
