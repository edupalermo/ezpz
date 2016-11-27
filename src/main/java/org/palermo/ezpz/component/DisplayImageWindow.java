package org.palermo.ezpz.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.palermo.ezpz.bean.Mask;

public class DisplayImageWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	
	private BufferedImage bufferedImage = null;
	
	private List<Point> exclusions = null;
	
	public DisplayImageWindow(String windowName, Mask mask) {
		this(windowName, mask.getTemplate());
		exclusions = mask.getExclusions();
	}

	public DisplayImageWindow(String windowName, BufferedImage bufferedImage) {
		super();

		this.bufferedImage = bufferedImage;
		
		this.setTitle("Image: " + windowName);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);

		Dimension dimension = new Dimension(bufferedImage.getWidth(), bufferedImage.getHeight());

		BorderLayout bl = (BorderLayout)this.getLayout();
		bl.setHgap(0);
		bl.setVgap(0);

		
		
		panel = this.createPanel();
		panel.setSize(dimension);
		panel.setMaximumSize(dimension);
		panel.setPreferredSize(dimension);
		this.add(panel, BorderLayout.CENTER);
	}
	
	public JPanel createPanel() {
		return new JPanel() {
			private static final long serialVersionUID = 1L;
			
			@Override
		    protected void paintComponent(Graphics g) {
				g.drawImage(bufferedImage, 0, 0, this);
				
				if (exclusions != null) {
					g.setColor(Color.WHITE);
					
					for (Point point : exclusions) {
						g.drawRect(point.x, point.y, 1, 1);
					}
				}
		    }
		};
	}
	
	
}
