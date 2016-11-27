package org.palermo.ezpz.shell.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.palermo.ezpz.console.Console;
import org.palermo.ezpz.shell.command.interfaces.Command;
import org.palermo.ezpz.threads.ScreenCaptureWorker;

public class CaptureStartCommand implements Command {
	
	private final static Pattern PATTERN = Pattern.compile("capture start"); 
	
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
		ScreenCaptureWorker worker = new ScreenCaptureWorker();
		worker.execute();
		console.info("Screen capture worker started...");
	}
}
