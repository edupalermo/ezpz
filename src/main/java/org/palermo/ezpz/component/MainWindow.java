package org.palermo.ezpz.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.palermo.ezpz.Application;
import org.palermo.ezpz.navigation.NavigationFacade;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel;

	private NavigationFacade navigation = new NavigationFacade();

	public MainWindow() {
		super();

		this.addListeners();
		this.setTitle("Display");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);

		panel = this.createPanel();
		this.add(panel, BorderLayout.CENTER);

		this.setSize(new Dimension(Navigation.INITIAL_WIDTH, Navigation.INITIAL_HEIGHT));
		this.refreshCapture();

	}

	public NavigationFacade getNavigation() {
		return this.navigation;
	}

	private void refreshCapture() {
		this.panel.repaint();
	}

	public BufferedImage getScrren() {
		BufferedImage bufferedImage = null;
		synchronized (Application.robot) {
			bufferedImage = Application.robot.createScreenCapture(navigation.getScreen());
		}
		return bufferedImage;
	}

	public Rectangle getRegionOnFocus() {
		return navigation.getRegionOnFocus();
	}

	public Rectangle getCaptureRectangle() {
		return this.navigation.getRegionOnFocus();
	}

	public MainWindow getOuter() {
		return this;
	}

	public JPanel createPanel() {
		return new JPanel() {
			private static final long serialVersionUID = 1L;

			private int diffHeight = 0;
			private int diffWidth = 0;

			@Override
			protected void paintComponent(Graphics g) {

				int dh = (int) (getOuter().getSize().getHeight() - getOuter().getContentPane().getSize().getHeight());
				if (dh > 0)
					diffHeight = dh;

				int dw = (int) (getOuter().getSize().getWidth() - getOuter().getContentPane().getSize().getWidth());
				if (dw > 0)
					diffWidth = dw;

				System.out.println(String.format("%f   %f", getOuter().getSize().getHeight(), getOuter().getContentPane().getSize().getHeight()));
				System.out.println(String.format("%d   %d", diffWidth, diffHeight));

				//super.paintComponent(g);

				BufferedImage bi = navigation.getImageOnFocus();

				g.drawImage(bi, 0, 0, this);
				
				getOuter().getContentPane().setSize(new Dimension(bi.getWidth(), bi.getHeight()));
				getOuter().setSize(addDimension(getOuter().getContentPane().getSize(), diffWidth, diffHeight));


			}
		};
	}

	private Dimension addDimension(Dimension d, int width, int height) {
		Dimension answer = new Dimension(d);
		answer.width += width;
		answer.height += height;
		return answer;

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
