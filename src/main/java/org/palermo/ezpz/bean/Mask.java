package org.palermo.ezpz.bean;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.tuple.Pair;
import org.palermo.ezpz.utils.IoUtils;

public class Mask implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private BufferedImage template;
	
	private Set<Point> exclusions = new TreeSet<Point>();
	
	public Mask(BufferedImage bufferedImage) {
		this.template = bufferedImage;
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		Pair<byte[], Set<Point>> pair = Pair.of(IoUtils.bufferedImageToBytes(this.template), exclusions);
		out.writeObject(pair);
	}
	
	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		Pair<byte[], Set<Point>> pair =  (Pair<byte[], Set<Point>>) in.readObject();
		this.exclusions = pair.getRight();
		this.template = IoUtils.bytesToBufferedImage(pair.getLeft());
	}
	
	public boolean matches(BufferedImage bufferedImage) {
		boolean response = true;
		
		outter: for (int x = 0; x < this.template.getWidth(); x++) {
			for (int y = 0; y < this.template.getHeight(); y++) {
				if ((!exclusions.contains(new Point(x, y))) && 
						(this.template.getRGB(x, y) != bufferedImage.getRGB(x, y))) {
					response = false;
					break outter;
				}
			}
		}
		return response;
	}

	public boolean merge(BufferedImage bufferedImage) {
		boolean response = true;
		
		for (int x = 0; x < this.template.getWidth(); x++) {
			for (int y = 0; y < this.template.getHeight(); y++) {
				if ((!exclusions.contains(new Point(x, y))) && 
						(this.template.getRGB(x, y) != bufferedImage.getRGB(x, y))) {
					exclusions.add(new Point(x, y));
				}
			}
		}
		return response;
	}
	
	public boolean fit(BufferedImage bufferedImage) {
		return (template.getWidth() == bufferedImage.getWidth()) && 
				(template.getHeight() == bufferedImage.getHeight());
	}
	
	public BufferedImage getTemplate() {
		return this.template;
	}
	
	public List<Point> getExclusions() {
		return this.getExclusions();
	}
	
}
