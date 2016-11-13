package org.palermo.ezpz.component;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.palermo.ezpz.Application;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	
	private Navigation navigation = new Navigation();
	
	private BufferedImage cache = null;

	public MainWindow() {
		super();

		this.addListeners();
		
		this.setTitle("Display");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = this.createPanel();

		this.add(panel);

		this.setBounds(10, 10, 400, 250);
		this.refreshCapture();
		
	}

	private void refreshCapture() {
		Rectangle capture = navigation.getRectangle();
		this.setSize(capture.width, capture.height);
		this.panel.repaint();
	}
	
	public BufferedImage getBufferedImageOnFocus() {
		BufferedImage bufferedImage = null;
		try {
			bufferedImage =  new Robot().createScreenCapture(navigation.getRectangle());
		} catch (AWTException e) {
			Application.handleException(e);
		}
		return bufferedImage;
	}
	
	public void loadImage(BufferedImage bufferedImage) {
		Rectangle bounds = this.getBounds();
		this.setBounds(bounds.x, bounds.y, bufferedImage.getWidth(), bufferedImage.getHeight());
		this.cache = bufferedImage;
		this.panel.repaint();
		
	}
	
	public Rectangle getCaptureRectangle() {
		return this.navigation.getRectangle();
	}
	
	public JPanel createPanel() {
		return new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
		    protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				if (cache != null) {
					g.drawImage(cache, 0, 0, this);
					cache = null;
				}
				else {
					g.setColor(Color.WHITE);
					g.fillRect(0, 0, this.getWidth(), this.getHeight());
					
					BufferedImage bi = getBufferedImageOnFocus();
					g.drawImage(bi, 0, 0, this);
				}
		    }
		};
	}
	
	public void addListeners() {
		this.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				navigation.keyPressed(e);
				refreshCapture();
			}

			public void keyReleased(KeyEvent ke) {
				// TODO Auto-generated method stub

			}

			public void keyTyped(KeyEvent ke) {
				// TODO Auto-generated method stub

			}

		});
		
	}
	
}
