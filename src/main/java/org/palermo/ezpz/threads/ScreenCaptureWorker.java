package org.palermo.ezpz.threads;

import java.awt.image.BufferedImage;
import java.util.Set;

import javax.swing.SwingWorker;

import org.palermo.ezpz.Application;
import org.palermo.ezpz.config.BufferedImageDao;
import org.palermo.ezpz.config.MaskDao;
import org.palermo.ezpz.console.Console;

public class ScreenCaptureWorker extends SwingWorker<Void, String> {
	
	private static final Console console = new Console();

	@Override
	protected Void doInBackground() throws Exception {
		
		while (! isCancelled()) {
			
			long initial = System.currentTimeMillis();

			BufferedImage captured = Application.mainWindow.getScrren();
			
			boolean save = true;
			
			String maskName = null;
			if ((maskName = MaskDao.searchMaskThatMatches(captured)) != null) {
				console.info("Auto cap: image matches to the mask [%s]", maskName);
				save = false;
			}
			
			if (save && BufferedImageDao.containsScreen(captured)) {
				console.info("Auto cap: image already on buffer");
				save = false;
			}
			
			if (save) {
				BufferedImageDao.save(generateBufferName() ,captured);
			}
			
			console.info("Auto cap: took %d", (System.currentTimeMillis() - initial));
			
			Thread.sleep(5000);
		}
		
		Application.screenCaptureWorker = null;
		console.warn("Screen capture thread was stopped!");
		
		return null;
	}
	
	private String generateBufferName() {
		int i = 0;
		String answer = null;
		
		Set<String> bufferKeys = BufferedImageDao.getNames(); 
		do {
			
			answer = Integer.toString(i);
			i++;
			
		} while (bufferKeys.contains(answer));
		
		return answer;
	}

}
