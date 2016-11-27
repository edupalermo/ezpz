package org.palermo.ezpz.config;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.palermo.ezpz.utils.IoUtils;

@SuppressWarnings("unchecked")
public class ImageDao {
	
	private final static File FILE = new File("images.obj");
	
	private static Map<String, byte[]> images = null;
	
	static {
		if (FILE.exists()) {
			images = (Map<String, byte[]>) IoUtils.readObjectFromFile(FILE, Map.class);
		}
		else {
			images = new HashMap<String, byte[]>();
		}
	}
	
	public static void save(String name, BufferedImage bi) {
	images.put(name, IoUtils.bufferedImageToBytes(bi));
	}

	
	public static BufferedImage get(String name) {
		BufferedImage bufferedImage = null;
		
		byte[] bytes = images.get(name);
		if (bytes != null) {
			bufferedImage = IoUtils.bytesToBufferedImage(bytes);
		}
		return bufferedImage;
	}
	
	public static boolean delete(String name) {
		boolean removed = false;
		
		if (removed = images.remove(name) != null) {
			IoUtils.writeObjectToFile(FILE, images);
		}
		return removed;
	}
	
	public static boolean rename(String oldName, String newName) {
		boolean renamed = false;
		
		byte imageBytes[] = null;
		
		if ( (!images.containsKey(newName)) && 
				((imageBytes = images.remove(oldName)) != null) ) {
			images.put(newName, imageBytes);
			IoUtils.writeObjectToFile(FILE, images);
			renamed = true;
		}
		
		return renamed;
	}
	
	public static Set<String> getNames() {
		return images.keySet();
	}
	
	public static Set<Map.Entry<String, byte[]>> getEntries() {
		return images.entrySet();
	}
	
}
