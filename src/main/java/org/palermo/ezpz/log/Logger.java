package org.palermo.ezpz.log;

public class Logger {

	public void plain(String message, Object ... args) {
		this.info(String.format(message, args));
	}

	public void plain(String message, boolean lineBreak) {
		System.out.print(String.format("%s %s", message, lineBreak ? "\n" : ""));
		System.out.flush();
	}
	
	public void plain(String message) {
		System.out.println(String.format("%s", message));
		System.out.flush();
	}


	public void info(String message, Object ... args) {
		this.info(String.format(message, args));
	}

	public void info(String message) {
		System.out.println(String.format("INFO:  %s", message));
		System.out.flush();
	}

	public void error(String message, Object ... args) {
		this.error(String.format(message, args));
	}

	public void error(String message) {
		System.err.println(String.format("ERROR: %s", message));
		System.err.flush();
	}

}
