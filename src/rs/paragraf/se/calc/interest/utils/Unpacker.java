package rs.paragraf.se.calc.interest.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Unpacker {
	public static void unpackFromClassPath(String resource, String toFile)
			throws Unpacker.UnpackException {
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			is = Unpacker.class.getClassLoader().getResourceAsStream(resource);
			if (is == null) {
				throw new UnpackException("Resource does not exist");
			}
			File file = new File(toFile);
			if (!file.exists()) {
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			byte[] data = new byte[1024];
			int read = 0;
			while ((read = is.read(data)) != -1) {
				fos.write(data, 0, read);
			}
			fos.flush();
			return;
		} catch (FileNotFoundException e) {
			throw new UnpackException("Could not write to file " + toFile);
		} catch (IOException e) {
			throw new UnpackException("Could not create file " + toFile);
		} catch (SecurityException e) {
			throw new UnpackException("Could not unpack file " + resource);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void unpackJar(String toBasePath) {
	}

	public static class UnpackException extends Exception {
		public UnpackException(String message) {
			super();
		}
	}
}