package org.palermo.ezpz.shell.command;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.palermo.ezpz.config.Configuration;
import org.palermo.ezpz.console.Console;
import org.palermo.ezpz.shell.command.interfaces.Command;
import org.palermo.ezpz.utils.StringUtils;

public class ImageListCommand implements Command {
	
	private final static Pattern PATTERN = Pattern.compile("image list"); 
	
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
		Map<String, byte[]> map = Configuration.DEFAULT.getImages();
		if (map.size() == 0) {
			console.error("There is no images recorded!");
		}
		else {
			int largeImageNameSize = this.getLargeImageName();
			for (Map.Entry<String, byte[]> entry : map.entrySet()) {
				BufferedImage bi = Configuration.DEFAULT.loadImage(entry.getKey());
				console.plain("%s - %4d x %4d", 
						StringUtils.rpad(entry.getKey(), largeImageNameSize, ' '), 
						bi.getWidth(), bi.getHeight());
			}
		}
	}
	
	private int getLargeImageName() {
		int bigger = 0;
		for (String name : Configuration.DEFAULT.getImages().keySet()) {
			bigger = Math.max(bigger, name.length());
		}
		return bigger;
	}
	
}
