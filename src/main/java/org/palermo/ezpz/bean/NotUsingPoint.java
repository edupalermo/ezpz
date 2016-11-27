package org.palermo.ezpz.bean;

import org.apache.commons.lang3.builder.CompareToBuilder;

public class NotUsingPoint extends java.awt.Point implements Comparable<NotUsingPoint> {

	private static final long serialVersionUID = 1L;

	public NotUsingPoint(int x, int y) {
		super(x, y);
	}

	public int compareTo(NotUsingPoint point) {
		
		return new CompareToBuilder()
			.append(this.x, point.x)
			.append(this.y, point.y)
			.toComparison();
	}

}
