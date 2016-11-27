package org.palermo.ezpz.config;

import java.awt.Rectangle;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.palermo.ezpz.utils.IoUtils;

@SuppressWarnings("unchecked")
public class RegionDao {

	private final static File FILE = new File("regions.obj");
	
	private static Map<String, Rectangle> regions = null;
	
	static {
		if (FILE.exists()) {
			regions = (Map<String, Rectangle>) IoUtils.readObjectFromFile(FILE, Map.class);
		}
		else {
			regions = new HashMap<String, Rectangle>();
		}
	}
	
	public static void save(String name, Rectangle r) {
		regions.put(name, r);
		IoUtils.writeObjectToFile(FILE, regions);
	}

	public static Rectangle get(String name) {
		return regions.get(name);
	}

	public static boolean delete(String name) {
		boolean removed = false;
		
		if (removed = regions.remove(name) != null) {
			IoUtils.writeObjectToFile(FILE, regions);
		}
		return removed;
	}

	public static Set<String> getNames() {
		return regions.keySet();
	}

	public static Set<Map.Entry<String, Rectangle>> getEntries() {
		return regions.entrySet();
	}

}
