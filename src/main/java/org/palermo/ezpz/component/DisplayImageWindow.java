package org.palermo.ezpz.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DisplayImageWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	
	private BufferedImage bufferedImage = null;

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
		    }
		};
	}
	
	
}
