package org.ex.yggdrasil.model.entities.players.specialization;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SpecializationLevels implements Serializable {

	private static final long serialVersionUID = 3046583792948878317L;
	
	public static final int MAX_LEVELS = 100;

	Map<String,AbstractSpecialization> levels;

	private int totalLevels = 0;
	
	public SpecializationLevels() {
		this.totalLevels=0;
		this.levels = new HashMap<>();
	}
	
	public int getTotalLevels() {
		return totalLevels;
	}
	
	public boolean addClass(String name) {
		if (totalLevels >= MAX_LEVELS) {
			return false;
		} else if (levels.containsKey(name)) {
			return false;
		}
		
		AbstractSpecialization result = Specializations.newInstance(name);
		if (result == null) {
			return false;
		}
		
		totalLevels += result.getCurrentLevel();
		return true;
	}
	
	public Specialization getClass(String name) {
		return levels.get(name);
	}
	
	public synchronized boolean levelUp(String name) {
		boolean result;
		
		if (totalLevels >= MAX_LEVELS) {
			result = false;
		} else {			
			AbstractSpecialization s = levels.get(name);
			
			if (s == null) {
				result = false;
			} else {
				result = s.levelUp();
				
				if (result) {
					totalLevels++;
				}
			}
		}

		return result;
	}
}
