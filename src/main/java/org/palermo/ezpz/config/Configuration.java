package org.palermo.ezpz.config;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.palermo.ezpz.Application;
import org.palermo.ezpz.utils.IoUtils;

public class Configuration implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Map<String, byte[]> images = new HashMap<String, byte[]>();
	
	private final static String FILE_FORMAT = "bmp"; 
	
	
	public void saveImage(String name, BufferedImage bi) {
		ByteArrayOutputStream bos = null; 
		try {
			bos = new ByteArrayOutputStream();
			ImageIO.write(Application.mainWindow.getBufferedImageOnFocus(), FILE_FORMAT, bos);
			bos.flush();
			
			images.put(name, bos.toByteArray());
		} catch (IOException e) {
			Application.handleException(e);
		} finally {
			if (bos != null) {
				IoUtils.closeQuitely(bos);
			}
		}
	}
	
	public BufferedImage loadImage(String name) {
		
		BufferedImage bufferedImage = null;
		
		byte[] bytes = images.get(name);
		
		if (bytes != null) {
			ByteArrayInputStream bis = null;
			try {
				bis = new ByteArrayInputStream(bytes);
				bufferedImage = ImageIO.read(bis);
			} catch (IOException e) {
				Application.handleException(e);
			} finally {
				IoUtils.closeQuitely(bis);
			}
		}
		return bufferedImage;
	}
	
	public Map<String, byte[]> getImages() {
		return images;
	}
	

}
