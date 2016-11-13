package org.palermo.ezpz.shell;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.palermo.ezpz.Application;
import org.palermo.ezpz.config.Configuration;
import org.palermo.ezpz.exception.CustomException;
import org.palermo.ezpz.log.Logger;
import org.palermo.ezpz.shell.command.FixCommand;
import org.palermo.ezpz.shell.command.interfaces.Command;
import org.palermo.ezpz.utils.ImageUtils;
import org.palermo.ezpz.utils.IoUtils;
import org.palermo.ezpz.utils.StringUtils;

public class Prompt implements Runnable {
	
	private final static Pattern PATTERN_IMAGE_SAVE = Pattern.compile("image save (\\w+)"); 
	private final static Pattern PATTERN_IMAGE_LIST = Pattern.compile("image list"); 
	private final static Pattern PATTERN_IMAGE_DELETE = Pattern.compile("image delete (\\w+)"); 
	private final static Pattern PATTERN_IMAGE_LOAD = Pattern.compile("image load (\\w+)"); 
	private final static Pattern PATTERN_SHOW = Pattern.compile("show"); 
	private final static Pattern PATTERN_FIX = Pattern.compile("fix"); 
	private final static Pattern PATTERN_REGION_SAVE = Pattern.compile("region save (\\w+)"); 
	private final static Pattern PATTERN_REGION_LIST = Pattern.compile("region list"); 
	private final static Pattern PATTERN_REGION_DELETE = Pattern.compile("region delete (\\w+)"); 
	
	private final static String IMAGE_APPLICATION_BAR = "applicationBar";
	
	private final static File CONFIGURATION_FILE = new File("configuration.obj");
	
	private static final Logger logger = new Logger();
	
	private Configuration configuration = new Configuration();
	
	private boolean stop = false;
	
	
	public Prompt() throws CustomException {
		if (CONFIGURATION_FILE.exists()) {
			this.configuration = (Configuration) IoUtils.readObjectToFile(CONFIGURATION_FILE);
		}
		else {
			this.configuration = new Configuration();
		}
		
	}

	public void run() {
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			
			ArrayList<Command> commandList = new ArrayList<Command>();
			commandList.add(new FixCommand(this.configuration));
			
			logger.plain("Interactive Shell");
			logger.plain("=========== =====");
			
			while (!stop) {
				logger.plain("#> ", false);
				String stringCommand = br.readLine();
				
				boolean knowCommand = 
						handleImageCommands(stringCommand) || 
						handleShowCommands(stringCommand) ||
						handleFixCommands(stringCommand) ||
						handleRegionCommands(stringCommand);
				
				if (!knowCommand) {
					for (Command command : commandList) {
						if (command.executed(stringCommand)) {
							knowCommand = true;
							break;
						}
					}
				}
				
				if (!knowCommand) {
					handleUnknowCommand(stringCommand);
				}
				
			}
		} catch (IOException e) {
			Application.handleException(e);
		}

	}
	
	private boolean handleImageCommands(String command) {
		boolean handled = true;
		
		Matcher matcher = null;
		if ((matcher = PATTERN_IMAGE_SAVE.matcher(command)).matches()) {
			String imageName = matcher.group(1);
			configuration.saveImage(imageName, Application.mainWindow.getBufferedImageOnFocus());
			IoUtils.writeObjectToFile(CONFIGURATION_FILE, configuration);
			logger.info("Image [%s] saved with size [%d] bytes.", imageName, configuration.getImages().get(imageName).length);
		}
		else if ((matcher = PATTERN_IMAGE_LIST.matcher(command)).matches()) {
			this.imageList();
		}
		else if ((matcher = PATTERN_IMAGE_DELETE.matcher(command)).matches()) {
			String imageName = matcher.group(1);
			Map<String, byte[]> imageMap = configuration.getImages();
			if (!imageMap.containsKey(imageName)) {
				logger.error("Image [%s] not found!", imageName);
			}
			else {
				imageMap.remove(imageName);
				IoUtils.writeObjectToFile(CONFIGURATION_FILE, configuration);
				logger.info("Image [%s] removed and database updated.", imageName);
			}
		}
		else {
			handled = false;
		}
		return handled;
	}
	
	private boolean handleShowCommands(String command) {
		boolean handled = true;
		
		Matcher matcher = null;
		if ((matcher = PATTERN_SHOW.matcher(command)).matches()) {
			Rectangle r = Application.mainWindow.getCaptureRectangle();
			logger.info("Capture %d %d %d %d", r.x, r.y, r.width, r.height);
		}
		else {
			handled = false;
		}
		return handled;
	}
	
	private boolean handleUnknowCommand(String command) {
		logger.error("Unknow command [%s]!", command);
		return true;
	}
	
	private boolean handleFixCommands(String command) {
		boolean handled = true;
		
		Matcher matcher = null;
		if ((matcher = PATTERN_FIX.matcher(command)).matches()) {
			BufferedImage search = this.configuration.loadImage(IMAGE_APPLICATION_BAR);
			
			if (search == null) {
				logger.error("Image [%s] not recorded! ", IMAGE_APPLICATION_BAR);
			}
			else {
				GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
				Point location = ImageUtils.locate(Application.robot.createScreenCapture(ImageUtils.getScreenSize()), search);
				
				if (location == null) {
					logger.error("Not found slice of screen that match [%s]", IMAGE_APPLICATION_BAR);
				}
				else {
					logger.info("Window found on (%d, %d)!", location.x, location.y);
				}
			}
		}
		else {
			handled = false;
		}
		return handled;
	}
	
	private boolean handleRegionCommands(String command) {
		boolean handled = true;
		
		Matcher matcher = null;
		if ((matcher = PATTERN_IMAGE_SAVE.matcher(command)).matches()) {
			String imageName = matcher.group(1);
			configuration.saveImage(imageName, Application.mainWindow.getBufferedImageOnFocus());
			IoUtils.writeObjectToFile(CONFIGURATION_FILE, configuration);
			logger.info("Image [%s] saved with size [%d] bytes.", imageName, configuration.getImages().get(imageName).length);
		}
		else if ((matcher = PATTERN_IMAGE_LIST.matcher(command)).matches()) {
			this.imageList();
		}
		else if ((matcher = PATTERN_IMAGE_DELETE.matcher(command)).matches()) {
			String imageName = matcher.group(1);
			Map<String, byte[]> imageMap = configuration.getImages();
			if (!imageMap.containsKey(imageName)) {
				logger.error("Image [%s] not found!", imageName);
			}
			else {
				imageMap.remove(imageName);
				IoUtils.writeObjectToFile(CONFIGURATION_FILE, configuration);
				logger.info("Image [%s] removed and database updated.", imageName);
			}
		}
		else if ((matcher = PATTERN_IMAGE_LOAD.matcher(command)).matches()) {
			String imageName = matcher.group(1);
			Map<String, byte[]> imageMap = configuration.getImages();
			if (!imageMap.containsKey(imageName)) {
				logger.error("Image [%s] not found!", imageName);
			}
			else {
				Application.mainWindow.loadImage(this.configuration.loadImage(imageName));
				logger.info("Image [%s] loaded.", imageName);
			}
		}
		else {
			handled = false;
		}
		return handled;
	}
	
	private void imageList() {
		Map<String, byte[]> map = configuration.getImages();
		if (map.size() == 0) {
			logger.error("There is no images recorded!");
		}
		else {
			int largeImageNameSize = this.getLargeImageName();
			for (Map.Entry<String, byte[]> entry : map.entrySet()) {
				BufferedImage bi = this.configuration.loadImage(entry.getKey());
				logger.plain("%s - %4d x %4d bytes", 
						StringUtils.rpad(entry.getKey(), largeImageNameSize, ' '), 
						bi.getWidth(), bi.getHeight());
			}
		}
	}
	
	private int getLargeImageName() {
		int bigger = 0;
		for (String name : configuration.getImages().keySet()) {
			bigger = Math.max(bigger, name.length());
		}
		return bigger;
	}

}
