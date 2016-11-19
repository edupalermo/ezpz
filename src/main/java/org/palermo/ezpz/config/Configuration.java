package org.palermo.ezpz.config;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.palermo.ezpz.Application;
import org.palermo.ezpz.utils.IoUtils;

public enum Configuration implements Serializable {
	
	DEFAULT;
	
	private final File IMAGES_FILE = new File("image.obj");

	private final File REGIONS_FILE = new File("region.obj");

	private Map<String, byte[]> images = null;
	
	private Map<String, Rectangle> regions = null;
	
	private final static String FILE_FORMAT = "bmp"; 
	
	@SuppressWarnings("unchecked")
	private Configuration() {
		if (IMAGES_FILE.exists()) {
			this.images = (Map<String, byte[]>) IoUtils.readObjectToFile(IMAGES_FILE);
		}
		else {
			this.images = new HashMap<String, byte[]>();
		}
		
		if (REGIONS_FILE.exists()) {
			this.regions = (Map<String, Rectangle>) IoUtils.readObjectToFile(REGIONS_FILE);
		}
		else {
			this.regions = new HashMap<String, Rectangle>();
		}
	}
	
	public void saveImage(String name, BufferedImage bi) {
		ByteArrayOutputStream bos = null; 
		try {
			bos = new ByteArrayOutputStream();
			ImageIO.write(Application.mainWindow.getBufferedImageOnFocus(), FILE_FORMAT, bos);
			bos.flush();
			
			images.put(name, bos.toByteArray());
			
			IoUtils.writeObjectToFile(IMAGES_FILE, this.images);
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
	
	public boolean removeImage(String name) {
		boolean removed = false;
		
		if (removed = this.images.remove(name) != null) {
			IoUtils.writeObjectToFile(IMAGES_FILE, this.images);
		}
		return removed;
	}
	
	public Map<String, byte[]> getImages() {
		return images;
	}
	
	public Map<String, Rectangle> getRegions() {
		return regions;
	}
	
	public void saveRegion(String name, Rectangle r) {
		regions.put(name, r);
		IoUtils.writeObjectToFile(REGIONS_FILE, this.regions);
	}

	public Rectangle getRegion(String name) {
		return this.regions.get(name);
	}

}
