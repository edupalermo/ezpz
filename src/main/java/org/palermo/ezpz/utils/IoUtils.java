package org.palermo.ezpz.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.palermo.ezpz.Application;

public class IoUtils {
	
	private final static String FILE_FORMAT = "bmp"; 
	
	public static final void writeObjectToFile(File file, Object serializable) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(serializable);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			closeQuitely(oos);
		}
	}
	
	public static final <T> T readObjectFromFile(File file, Class<T> clazz) {
		T object = null;
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			object = clazz.cast(ois.readObject());
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} finally {
			closeQuitely(ois);
		}
		return object;
	}
	
	public static final void closeQuitely(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static final void closeQuitely(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static final BufferedImage bytesToBufferedImage(byte[] array) {
		BufferedImage bufferedImage = null;
		
		if (array != null) {
			ByteArrayInputStream bis = null;
			try {
				bis = new ByteArrayInputStream(array);
				bufferedImage = ImageIO.read(bis);
			} catch (IOException e) {
				Application.handleException(e);
			} finally {
				IoUtils.closeQuitely(bis);
			}
		}
		return bufferedImage;
	}

	public static final byte[] bufferedImageToBytes(BufferedImage bufferedImage) {
		byte array[] = null;
		ByteArrayOutputStream bos = null;
		try {
			bos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, FILE_FORMAT, bos);
			bos.flush();
			
			array =  bos.toByteArray();
		} catch (IOException e) {
			Application.handleException(e);
		} finally {
			if (bos != null) {
				IoUtils.closeQuitely(bos);
			}
		}
		return array;
	}

}
