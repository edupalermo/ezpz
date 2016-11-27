package org.palermo.ezpz.shell.command;

import java.awt.image.BufferedImage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.palermo.ezpz.bean.Mask;
import org.palermo.ezpz.config.ImageDao;
import org.palermo.ezpz.config.MaskDao;
import org.palermo.ezpz.console.Console;
import org.palermo.ezpz.shell.command.interfaces.Command;

public class MaskMergeCommand implements Command {
	
	private final static Pattern PATTERN = Pattern.compile("mask create (\\w+) (\\w+)"); 
	
	private Console console = new Console();
	
	public boolean executed(String command) {
		boolean executed = false;

		Matcher matcher = PATTERN.matcher(command);

		if (matcher.matches()) {
			executed = true;
			this.execute(matcher.group(1), matcher.group(2));
		}

		return executed;
	}

	
	private void execute(String maskName, String imageName) {
		BufferedImage bufferedImage = ImageDao.get(imageName);
		
		if (bufferedImage == null) {
			console.error("Image [%s] does not exist.", imageName);
			return;
		}
		
		if (MaskDao.getNames().contains(maskName)) {
			console.error("Mask [%s] already exist.", imageName);
			return;
		}
		
		Mask mask = new Mask(bufferedImage);
		MaskDao.save(maskName, mask);
		console.info("Mask [%s] created.", maskName);
	}
}
