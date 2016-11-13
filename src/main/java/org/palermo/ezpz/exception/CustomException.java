package org.palermo.ezpz.exception;

public class CustomException extends Exception {

	private static final long serialVersionUID = 1L;

	public CustomException(String message, Throwable t) {
		super(message, t);
	}

	public CustomException(Throwable t) {
		super(t);
	}

}
