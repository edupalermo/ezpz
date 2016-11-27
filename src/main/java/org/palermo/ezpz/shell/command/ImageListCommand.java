package org.palermo.ezpz.shell.command;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.palermo.ezpz.config.ImageDao;
import org.palermo.ezpz.console.Console;
import org.palermo.ezpz.shell.command.interfaces.Command;
import org.palermo.ezpz.utils.IoUtils;
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
		Set<Map.Entry<String, byte[]>> entries = ImageDao.getEntries();
		if (entries.size() == 0) {
			console.error("There is no images recorded!");
		}
		else {
			int largeImageNameSize = this.getLargeImageName();
			for (Map.Entry<String, byte[]> entry : entries) {
				BufferedImage bi = IoUtils.bytesToBufferedImage(entry.getValue());
				console.plain("%s - %4d x %4d", 
						StringUtils.rpad(entry.getKey(), largeImageNameSize, ' '), 
						bi.getWidth(), bi.getHeight());
			}
		}
	}
	
	private int getLargeImageName() {
		int bigger = 0;
		for (String name : ImageDao.getNames()) {
			bigger = Math.max(bigger, name.length());
		}
		return bigger;
	}
	
}
