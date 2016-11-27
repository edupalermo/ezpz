package org.palermo.ezpz.config;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.palermo.ezpz.bean.Mask;
import org.palermo.ezpz.utils.IoUtils;

@SuppressWarnings("unchecked")
public class MaskDao {
	
	private final static File FILE = new File("masks.obj");
	
	private static Map<String, Mask> masks = null;
	
	static {
		if (FILE.exists()) {
			masks = (Map<String, Mask>) IoUtils.readObjectFromFile(FILE, Map.class);
		}
		else {
			masks = new HashMap<String, Mask>();
		}
	}
	
	public static void save(String name, Mask m) {
		masks.put(name, m);
		IoUtils.writeObjectToFile(FILE, masks);
	}
	
	public static boolean merge(String name, BufferedImage bufferedImage) {
		boolean answer = false;
		Mask mask = masks.get(name);
		if ((mask != null) && (mask.fit(bufferedImage))) {
			mask.merge(bufferedImage);
			IoUtils.writeObjectToFile(FILE, masks);
			answer = true;
		}
		return answer;
	}

	public static Mask get(String name) {
		return masks.get(name);
	}

	public static boolean delete(String name) {
		boolean removed = false;
		
		if (removed = masks.remove(name) != null) {
			IoUtils.writeObjectToFile(FILE, masks);
		}
		return removed;
	}
	
	public static String searchMaskThatMatches(BufferedImage bufferedImage) {
		for (Map.Entry<String, Mask> entry : masks.entrySet()) {
			Mask mask = entry.getValue();
			if (mask.fit(bufferedImage) && mask.matches(bufferedImage)) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	public static Set<String> getNames() {
		return masks.keySet();
	}

}
