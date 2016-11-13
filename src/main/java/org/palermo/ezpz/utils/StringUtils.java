package org.palermo.ezpz.utils;

public class StringUtils {
	
	public static final String rpad(String input, int size, char filler) {
		String output = input;
		
		if ((input != null) && (input.length() < size)) {
			StringBuilder sb = new StringBuilder();
			sb.append(input);
			for (int i = input.length(); i < size; i++) {
				sb.append(filler);
			}
			output = sb.toString();
		}
		
		return output;
	}
	
	public static final String lpad(String input, int size, char filler) {
		String output = input;
		
		if ((input != null) && (input.length() < size)) {
			StringBuilder sb = new StringBuilder();
			sb.append(input);
			for (int i = input.length(); i < size; i++) {
				sb.insert(0, filler);
			}
			output = sb.toString();
		}
		
		return output;
	}

	public static final String lpad(int content, int size, char filler) {
		return lpad(Integer.toString(content), size, filler);
	}

}
