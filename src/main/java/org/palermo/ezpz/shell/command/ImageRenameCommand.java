package org.palermo.ezpz.shell.command;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.palermo.ezpz.config.ImageDao;
import org.palermo.ezpz.console.Console;
import org.palermo.ezpz.shell.command.interfaces.Command;

public class ImageRenameCommand implements Command {
	
	private final static Pattern PATTERN = Pattern.compile("image rename (\\w+) (\\w+)"); 
	
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

	
	private void execute(String oldName, String newName) {
		Set<String> names = ImageDao.getNames();
		
		if (!names.contains(oldName)) {
			console.error("Image [%s] does not exist", oldName);
			return;
		}
		
		if (names.contains(newName)) {
			console.error("Cannot overwrite image [%s]", newName);
			return;
		}
		
		ImageDao.rename(oldName, newName);
		console.info("Image [%s] renamed to [%s]", oldName, newName);
	}
}
