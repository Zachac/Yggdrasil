package org.ex.yggdrasil.model.world;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

public class Persistence {

	public static final String SYSTEM_SAVE_FILE_PROPERTY_NAME = "yggdrasil.file.save";
	
	public static void save() throws FileNotFoundException {
		File f = getSaveFile();
		
		if (f == null) {
			throw new FileNotFoundException();
		}
		
		save(f);
	}
	
	public static void save(File f) throws FileNotFoundException {
		Objects.requireNonNull(f);
		
		if (f.isDirectory()) {
			throw new IllegalArgumentException("Error, cannot replace a directory!");
		}
		
		if (f.exists()) {
			File backup = new File(f.getAbsolutePath() + ".bkp");
			if (backup.exists()) {
				backup.delete();
			}
			f.renameTo(backup);
		}
		
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f))) {
			out.writeObject(World.get());
		} catch (Exception e) {
			FileNotFoundException e2 = new FileNotFoundException("Unable to save file.");
			e2.initCause(e);
			throw e2;
		}
	}

	public static void load() throws FileNotFoundException {
		File f = getSaveFile();
		
		if (f == null) {
			throw new FileNotFoundException();
		}
		
		load(f);
	}	
	
	public static void load(File f) throws FileNotFoundException {
		Objects.requireNonNull(f);
		
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {
			World w = (World) in.readObject();
			w.setFilename(f.getAbsolutePath());
			World.set(w);
		} catch (Exception e) {
			FileNotFoundException e2 = new FileNotFoundException("Unable to load file.");
			e2.initCause(e);
			throw e2;
		}
	}	
	
	public static File getSaveFile() {
		
		File result = null;
		
		String saveFile = System.getProperty(SYSTEM_SAVE_FILE_PROPERTY_NAME);
		
		if (saveFile != null) {
			try {
				result = new File(saveFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public static File getLoadFile() {
		
		File result = null;
		
		String loadFile = System.getProperty(SYSTEM_SAVE_FILE_PROPERTY_NAME);
		
		if (loadFile != null) {
			result = new File(loadFile);
		}
		
		return result;
	}
}
