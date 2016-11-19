package org.palermo.ezpz.console;

import org.palermo.ezpz.Application;

public class Console {

	public void plain(String message, Object ... args) {
		this.info(String.format(message, args));
	}

	public void plain(String message, boolean lineBreak) {
		Application.logHistory(String.format("%s %s", message, lineBreak ? "\n" : ""));
	}
	
	public void plain(String message) {
		Application.logHistory(String.format("%s", message));
	}


	public void info(String message, Object ... args) {
		this.info(String.format(message, args));
	}

	public void info(String message) {
		Application.logHistory(String.format("INFO:  %s\n", message));
	}

	public void error(String message, Object ... args) {
		this.error(String.format(message, args));
	}

	public void error(String message) {
		Application.logHistory(String.format("ERROR: %s\n", message));
	}

}
