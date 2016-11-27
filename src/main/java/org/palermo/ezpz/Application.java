package org.palermo.ezpz;

import java.awt.AWTException;
import java.awt.Robot;

import javax.swing.JOptionPane;

import org.palermo.ezpz.component.MainWindow;
import org.palermo.ezpz.component.PromptWindow;
import org.palermo.ezpz.threads.ScreenCaptureWorker;

public class Application {

	public static MainWindow mainWindow = null;
	public static PromptWindow promptWindow = null;
	public static Robot robot = null;
	public static ScreenCaptureWorker screenCaptureWorker = null;

	public static void main(String[] args) {

		try {
			robot = new Robot();
		} catch (AWTException e) {
			handleException(e);
		}

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				mainWindow = new MainWindow();
				mainWindow.setVisible(true);

				promptWindow = new PromptWindow();
				promptWindow.setVisible(true);

			}
		});
	}

	public static void handleException(Throwable t) {

		JOptionPane.showMessageDialog(mainWindow, t.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);

		t.printStackTrace();

		if (mainWindow != null) {
			mainWindow.dispose();
		}
	}
	
	public static void logHistory(String text) {
		if (promptWindow != null) {
			promptWindow.log(text);
		}
		else {
			System.err.println("PromptWindow is null!");
			System.err.println(text);
		}
	}
}
