package org.palermo.ezpz;

import java.awt.AWTException;
import java.awt.Robot;

import javax.swing.JOptionPane;

import org.palermo.ezpz.component.MainWindow;
import org.palermo.ezpz.component.PromptWindow;
import org.palermo.ezpz.exception.CustomException;
import org.palermo.ezpz.shell.Prompt;

public class Application {
	
	public static MainWindow mainWindow = null;
	public static Thread promptThread = null;
	public static PromptWindow promptWindow = null;
	
	public static Robot robot = null; 
    
    public static void main(String[] args) {
    	
		try {
			robot = new Robot();
		} catch (AWTException e) {
			handleException(e);
		}
		
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	
            	try {
            		
					mainWindow = new MainWindow();
					mainWindow.setVisible(true);
					
					promptWindow = new PromptWindow();
					promptWindow.setVisible(true);
					
					promptThread = new Thread(new Prompt());
					promptThread.start();
					
					
				} catch (CustomException e) {
					e.printStackTrace();
				}
            	
            	
            }
        });
    }
    
    public static void handleException(Throwable t) {
    	
    	JOptionPane.showMessageDialog(mainWindow, t.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
    	
    	t.printStackTrace();
    	
    	if (promptThread != null) {
    		promptThread.interrupt();
    	}
    	
    	if (mainWindow != null) {
        	mainWindow.dispose();
    	}
    }
}
