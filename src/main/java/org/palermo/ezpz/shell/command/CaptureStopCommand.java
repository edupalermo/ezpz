package org.palermo.ezpz.shell.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.palermo.ezpz.Application;
import org.palermo.ezpz.console.Console;
import org.palermo.ezpz.shell.command.interfaces.Command;
import org.palermo.ezpz.threads.ScreenCaptureWorker;

public class CaptureStopCommand implements Command {
	
	private final static Pattern PATTERN = Pattern.compile("capture stop"); 
	
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
		
		ScreenCaptureWorker screenCaptureWorker = Application.screenCaptureWorker;
		
		if (screenCaptureWorker == null) {
			console.error("ScreenCapture is not running.");
		}
		else {
			screenCaptureWorker.cancel(false);
			console.info("Trying to stop ScreenCapture worker...");
		}
	}
}
