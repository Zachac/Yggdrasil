package org.ex.yggdrasil.model.charachters;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class ClassLevels implements Serializable {

	private static final long serialVersionUID = 3046583792948878317L;

	Map<Class,Byte> classLevels;
	
	public static Byte LEVEL_CAP = 100;
	private Byte totalLevels;
	
	public ClassLevels() {
		classLevels = new TreeMap<Class,Byte>();
		totalLevels=0;
	}
	
	public boolean levelUp(Class c) {
		
		Objects.requireNonNull(c);
		
		if (totalLevels >= LEVEL_CAP) {
			return false;
		}
		
		Byte level = classLevels.get(c);
		
		if (level == null) {
			level = 0;
		}
		
		level++;
		
		if (level > c.getLevelCap()) {
			return false;
		}
		
		classLevels.put(c, level);
		totalLevels++;
		
		return true;
	}
	
}
